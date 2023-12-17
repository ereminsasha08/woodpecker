package com.woodpecker.model.abstractentity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;


@MappedSuperclass
@Getter
@Setter
public abstract class Product extends BaseEntity implements Serializable {

    @Column(name = "description", length = 800)
    private String description;

    @Column(name = "price", nullable = false)
    @NotNull
    int price;

    @Column(name = "contact", nullable = false)
    @NotNull
    @NotBlank
    String contact;


}
