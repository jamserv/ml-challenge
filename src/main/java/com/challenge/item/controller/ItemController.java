package com.challenge.item.controller;

import com.challenge.api.commons.Constants;
import com.challenge.api.commons.ControllerBase;
import com.challenge.item.model.Item;
import com.challenge.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.URI_ITEMS)
@Lazy
public class ItemController extends ControllerBase<Item> {

    @Autowired
    public ItemController(ItemService service) {
        super(service);
    }
}
