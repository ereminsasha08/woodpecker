package org.woodpecker.repository.model.order;

import jakarta.persistence.*;
import lombok.Data;
import org.woodpecker.repository.model.abstractentity.BaseEntity;
import org.woodpecker.repository.model.goods.Goods;

@Entity
@Table(name = "order_items")
@Data
public class OrderItems extends BaseEntity {
    @Column(name = "goods_id")
    private Goods goods;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    private Integer price;
    private Integer count;

}
