package org.woodpecker.repository.model.goods;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Entity
@Table(name = "map_world", schema = "goods")
@AttributeOverrides(
        {@AttributeOverride(name = "type_goods",
                column = @Column(name = "MAP_COUNTRY", nullable = false)),
                @AttributeOverride(name = "type_map",
                        column = @Column(name = "COUNTRY", nullable = false))}
)
@Data
public class WorldMap extends Map {
    @Column(name = "is_state", nullable = false)
    @NotNull
    private Boolean isState;
    @Column(name = "is_capital", nullable = false)
    private boolean isCapital = true;
    @Column(name = "is_view", columnDefinition = "boolean default true")
    @JsonIgnore
    private boolean isView;
}
