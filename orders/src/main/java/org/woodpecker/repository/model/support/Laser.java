package org.woodpecker.repository.model.support;

import org.woodpecker.repository.model.abstractentity.NamedEntity;
import org.woodpecker.repository.model.goods.WorldMap;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Laser extends NamedEntity {
    @Column(name = "max_size")
    private Integer maxSize;
    @Column(name = "capacity")
    private Integer capacity;

    public void setCapacity(WorldMap worldMap, Integer coefficient) {

        Integer addedCapacity = coefficient;

        if (worldMap.getIsState())
            addedCapacity *= 2;
        if (worldMap.getIsMultiLevel())
            addedCapacity *= 4;

        int size = worldMap.getSize().getHeight() * worldMap.getSize().getLength() / 100;
        addedCapacity *= size;
        this.capacity += addedCapacity;
    }

}
