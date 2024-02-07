package org.woodpecker.repository.model.goods;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class Size {

    private int height;

    private int length;
    private int weight;

}
