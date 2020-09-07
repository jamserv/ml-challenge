package com.challenge.item.service;

import com.challenge.api.commons.ChallengeException;
import com.challenge.api.commons.MercadolibreService;
import com.challenge.children.model.Children;
import com.challenge.children.repository.ChildrenRepository;
import com.challenge.health.component.HealthHandler;
import com.challenge.item.model.Item;
import com.challenge.item.model.ItemBase;
import com.challenge.item.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Service
@Lazy
public class ItemServiceHandler implements ItemService {

    @Autowired
    private ItemRepository repository;

    @Autowired
    private ChildrenRepository childrenRepository;

    @Autowired
    HealthHandler healthServiceHandler;

    private final Retrofit retrofit;
    private final MercadolibreService mercadolibreService;

    public ItemServiceHandler() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.mercadolibre.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mercadolibreService = retrofit.create(MercadolibreService.class);
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
                return callExternalApi(id);
            }
        } catch (Exception e) {
            Long time = System.currentTimeMillis();
            registerCall(time);
            throw ChallengeException.builder()
                    .message("Sorry, item not found")
                    .status_code(500)
                    .build();
        }
    }

    private Optional<Item> callExternalApi(String id) throws Exception {
        Long time = System.currentTimeMillis();

        Call<ItemBase> callItem = mercadolibreService.findItemById(id);
        ItemBase itemRequest = callItem.execute().body();
        registerCall(time);

        Item itemRemote = ItemBase.buildItem(itemRequest);
        save(itemRemote);

        time = System.currentTimeMillis();
        Call<List<ItemBase>> callChidrens = mercadolibreService.findChildrenItemByParentId(id);
        List<ItemBase> childrens = callChidrens.execute().body();
        registerCall(time);
        List<Children> childrenCollection = new ArrayList<>();

        childrens.stream().map((itemBase) -> ItemBase.buildItemChildren(itemBase)).map((children) -> {
            children.setItem(itemRemote);
            return children;
        }).map((children) -> {
            childrenCollection.add(children);
            return children;
        }).forEachOrdered((children) -> {
            childrenRepository.save(children);
        });
        itemRemote.setChildren(childrenCollection);

        Optional<Item> optional = Optional.ofNullable(itemRemote);
        return Optional.ofNullable(optional.orElseGet(Item::new));
    }

    @Override
    public Item save(Item model) {
        return repository.save(model);
    }

    private void registerCall(Long time) {
        healthServiceHandler.registerExternalCall(time);
    }

}
