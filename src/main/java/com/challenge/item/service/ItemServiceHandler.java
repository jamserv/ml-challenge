package com.challenge.item.service;

import com.challenge.api.commons.BaseConnector;
import com.challenge.children.model.Children;
import com.challenge.children.repository.ChildrenRepository;
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

@Service
@Lazy
public class ItemServiceHandler extends BaseConnector implements ItemService {

    @Autowired
    private ItemRepository repository;
    @Autowired
    private ChildrenRepository childrenRepository;
    private final RestOperations restOperations;

    @Value("${ml.api.host}")
    private String apiHost;

    public ItemServiceHandler(RestTemplateBuilder restApiBuilder) {
        this.restOperations = restApiBuilder.build();
    }

    @Override
    public Item save(Item model) {
        return repository.save(model);
    }

    @Transactional
    @Override
    public Optional<Item> findById(String id) {
        Optional<Item> item = repository.findById(id);
        if (item.isPresent()) {
            item.get().setChildren(childrenRepository.findByItem(item.get()));
            return Optional.ofNullable(item.orElseGet(Item::new));
        } else {
            String url = apiHost + "/" + id;
            ResponseEntity<ItemBase> itemResponseEntity = this.restOperations.getForEntity(url, ItemBase.class);

            ItemBase itemRequest = itemResponseEntity.getBody();
            Item itemRemote = ItemBase.buildItem(itemRequest);
            save(itemRemote);

            url = apiHost + "/" + id + "/children";
            ResponseEntity<ItemBase[]> childrens = this.restOperations.getForEntity(url, ItemBase[].class);
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
    }

    @Override
    public Boolean existsById(String id) {
        return repository.existsById(id);
    }

    @Override
    public List<Item> findAll() {
        return repository.findAll();
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    protected RestOperations getRestOperations() {
        return this.restOperations;
    }
}
