package org.woodpecker.repository.model.goods;

import lombok.Data;

import jakarta.persistence.*;

@Entity
@Table(name = "map_country", schema = "goods")
@AttributeOverrides(
        {@AttributeOverride(name = "type_goods",
                column = @Column(name = "MAP_COUNTRY", nullable = false)),
                @AttributeOverride(name = "type_map",
                        column = @Column(name = "COUNTRY", nullable = false))}
)
@Data
public class CountryMap extends Map {
    @Column(name = "name")
    private String name;
}
