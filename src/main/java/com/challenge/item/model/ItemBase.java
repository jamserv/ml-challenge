package com.challenge.item.model;

import com.challenge.children.model.Children;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
public class ItemBase implements Serializable {

    private static final long serialVersionUID = 1L;

    private String item_id;
    private String title;
    private String category_id;
    private BigDecimal price;
    private Timestamp start_time;
    private Timestamp stop_time;
    private String id;
    private List<Children> childrens;

    public static Item buildItem(ItemBase content) {
        Item item = new Item();
        item.setItem_id(content.getId());
        item.setTitle(content.getTitle());
        item.setCategory_id(content.getCategory_id());
        item.setPrice(content.getPrice());
        item.setStart_time(content.getStart_time());
        item.setStop_time(content.getStop_time());

        return item;
    }

    public static Children buildItemChildren(ItemBase content) {
        Children children = new Children();
        children.setItem_id(content.getId());
        children.setStop_time(content.getStop_time());

        return children;
    }
}
