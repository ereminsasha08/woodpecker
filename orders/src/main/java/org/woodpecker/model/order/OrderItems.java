package org.woodpecker.model.order;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order_items")
public class OrderItems {
    @Id
    private Long id;
    private Goods goods;
    private Integer price;
    private Integer count;

}
