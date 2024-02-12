package org.woodpecker.repository.model.goods;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Entity
@Table(name = "map_world", schema = "goods")
@Data
public class WorldMap extends Map{

    @Column(name = "is_state", nullable = false)
    @NotNull
    private Boolean isState;
    @Column(name = "is_capital", nullable = false)
    private boolean isCapital = true;

}
