package com.challenge.children.model;

import com.challenge.item.model.Item;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "childen")
@Data
@NoArgsConstructor
public class Children implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "item_id")
    private String item_id;

    @Column(name = "stop_time")
    @NonNull
    private Timestamp stop_time;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item", nullable = false)
    @NonNull
    private Item item;


}
