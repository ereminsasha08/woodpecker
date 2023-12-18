package org.woodpecker.model.support;

import org.woodpecker.model.abstractentity.NamedEntity;
import org.woodpecker.model.map.GeographyMap;
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

    public void setCapacity(GeographyMap geographyMap, Integer coefficient) {

        Integer addedCapacity = coefficient;

        if (geographyMap.getIsState())
            addedCapacity *= 2;
        if (geographyMap.getIsMultiLevel())
            addedCapacity *= 4;

        int size = geographyMap.getSize() * geographyMap.getSize() / 100;
        addedCapacity *= size;
        this.capacity += addedCapacity;
    }

}
