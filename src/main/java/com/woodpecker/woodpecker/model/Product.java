package com.woodpecker.woodpecker.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@MappedSuperclass
@Getter
@Setter
public abstract class Product extends BaseEntity {


    @Column(name = "price", nullable = false)
    @NotNull
    int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
//    @NotNull
    private User manager;

    @Column(name = "contact", nullable = false)
    String contact;


}
