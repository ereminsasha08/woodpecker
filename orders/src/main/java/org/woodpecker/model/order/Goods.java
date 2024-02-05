package org.woodpecker.model.order;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.woodpecker.model.user.User;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "goods")
public class Goods {

    @Id
    private Long id;
    @Enumerated(EnumType.STRING)
    private TypeGoods typeGoods;
    @Embedded
    private Size size;
    private Integer price;
    private String description;

    private LocalDateTime dateCreate;

//    private User userCreate;



}
