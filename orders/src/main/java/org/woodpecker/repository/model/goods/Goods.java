package org.woodpecker.repository.model.goods;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.woodpecker.repository.model.abstractentity.BaseEntity;
import org.woodpecker.repository.model.user.User;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
public abstract class Goods extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "type_goods")
    protected TypeGoods typeGoods;
    @Embedded
    protected Size size;
    @NotNull
    protected Integer price;
    protected String description;
    @NotNull
    protected LocalDateTime dateCreate;
    @NotNull
    protected User userCreate;

}
