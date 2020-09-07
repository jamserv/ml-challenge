package com.challenge.item.service;

import com.challenge.api.commons.BaseConnector;
import com.challenge.api.commons.ChallengeException;
import com.challenge.children.model.Children;
import com.challenge.children.repository.ChildrenRepository;
import com.challenge.health.component.HealthHandler;
import com.challenge.item.model.Item;
import com.challenge.item.model.ItemBase;
import com.challenge.item.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestOperations;

import java.util.*;
import org.springframework.web.client.HttpClientErrorException;

@Service
@Lazy
public class ItemServiceHandler extends BaseConnector implements ItemService {

    @Autowired
    private ItemRepository repository;
    @Autowired
    private ChildrenRepository childrenRepository;
    @Autowired
    HealthHandler healthServiceHandler;
    private final RestOperations restOperations;

    @Value("${ml.api.host}")
    private String apiHost;

    public ItemServiceHandler(RestTemplateBuilder restApiBuilder) {
        this.restOperations = restApiBuilder.build();
    }

    @Transactional
    @Override
    public Optional<Item> findById(String id) throws ChallengeException {
        try {
            Optional<Item> item = repository.findById(id);
            if (item.isPresent()) {
                item.get().setChildren(childrenRepository.findByItem(item.get()));
                return Optional.ofNullable(item.orElseGet(Item::new));
            } else {
                String url = apiHost + "/" + id;
                Long time = System.currentTimeMillis();
                ResponseEntity<ItemBase> itemResponseEntity = this.restOperations.getForEntity(url, ItemBase.class);
                callExternalApi(time);

                ItemBase itemRequest = itemResponseEntity.getBody();
                Item itemRemote = ItemBase.buildItem(itemRequest);
                save(itemRemote);

                url = apiHost + "/" + id + "/children";
                time = System.currentTimeMillis();
                ResponseEntity<ItemBase[]> childrens = this.restOperations.getForEntity(url, ItemBase[].class);
                callExternalApi(time);
                List<Children> childrenCollection = new ArrayList<>();

                for (ItemBase itemBase : childrens.getBody()) {
                    Children children = ItemBase.buildItemChildren(itemBase);
                    children.setItem(itemRemote);
                    childrenCollection.add(children);

                    childrenRepository.save(children);
                }
                itemRemote.setChildren(childrenCollection);

                Optional<Item> optional = Optional.ofNullable(itemRemote);
                return Optional.ofNullable(optional.orElseGet(Item::new));
            }
        } catch (HttpClientErrorException e) {
            throw ChallengeException.builder()
                    .message(e.getLocalizedMessage())
                    .status_code(e.getRawStatusCode())
                    .build();
        } catch (Exception e) {
            throw ChallengeException.builder()
                    .message(e.getLocalizedMessage())
                    .status_code(500)
                    .build();
        }
    }

    @Override
    public Item save(Item model) {
        return repository.save(model);
    }

    private void callExternalApi(Long time) {
        healthServiceHandler.registerExternalCall(time);
    }

    @Override
    protected RestOperations getRestOperations() {
        return this.restOperations;
    }
}
