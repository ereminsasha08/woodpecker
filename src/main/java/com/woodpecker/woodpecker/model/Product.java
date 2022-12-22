package com.woodpecker.woodpecker.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;


@MappedSuperclass
@Getter
@Setter
public abstract class Product extends BaseEntity {


    @Column(name = "price", nullable = false)
    @NotNull
    int price;

    @Column(name = "manager", nullable = false)
    @NotNull
    String manager;

    @Column(name = "contact", nullable = false)
    @NotNull
    String contact;


}
