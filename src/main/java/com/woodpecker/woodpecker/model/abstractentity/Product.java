package com.woodpecker.woodpecker.model.abstractentity;

import com.woodpecker.woodpecker.model.user.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Fetch(FetchMode.JOIN)
//    @NotNull
    private User manager;

    @Column(name = "contact", nullable = false)
    @NotNull
    @NotBlank
    String contact;


}
