package com.challenge.item.model;

import com.challenge.children.model.Children;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "item")
@Data
@NoArgsConstructor
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "item_id")
    private String item_id;

    @Column(name = "title")
    @NonNull
    private String title;

    @Column(name = "category_id")
    @NonNull
    private String category_id;

    @Column(name = "price")
    @NonNull
    private BigDecimal price;

    @Column(name = "start_time")
    @NonNull
    private Timestamp start_time;

    @Column(name = "stop_time")
    @NonNull
    private Timestamp stop_time;

    @Transient
    private List<Children> children;

}
