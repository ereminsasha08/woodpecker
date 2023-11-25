package com.woodpecker.woodpecker.model.map;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.woodpecker.woodpecker.model.abstractentity.Product;
import com.woodpecker.woodpecker.model.order.GeographyMapProduction;
import com.woodpecker.woodpecker.model.order.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "maps")
@NoArgsConstructor
public class GeographyMap extends Product {

    @Column(name = "type_map", nullable = false)
    @NotNull
    @NotBlank
    private String typeMap;

    @Column(name = "size", nullable = false)
    @NotNull
    private int size;

    @Column(name = "language_map", nullable = false)
    @NotNull
    @NotBlank
    private String language;

    @Column(name = "is_multi_level", nullable = false)
    @NotNull
    private Boolean isMultiLevel;

    @Column(name = "color", nullable = false)
    @NotNull
    @NotBlank
    private String color;

    @Column(name = "date_time", nullable = false)
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateTime;

    @Column(name = "is_state", nullable = false)
    @NotNull
    private Boolean isState;

    @Column(name = "is_capital", columnDefinition = "boolean default true")
    private boolean isCapital = true;

    @Column(name = "light", nullable = false)
    @NotNull
    @NotBlank
    private String light;

    @Column(name = "additional", length = 800)
    private String additional;

    @Column(name = "is_color_plywood")
    private Boolean isColorPlywood;
    @OneToOne(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "geography_map_production_id", referencedColumnName = "geography_map_id")
    @JsonIgnoreProperties("geographyMap")
    private GeographyMapProduction geographyMapProduction;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    @JsonIgnore
    private Order order;
    @Column(name = "is_view", columnDefinition = "boolean default true")
    @JsonIgnore
    private boolean isView = true;

    @Column(name = "is_monochromatic", columnDefinition = "boolean default false")
    private Boolean isMonochromatic;

    @Column(name = "is_plexiglas", columnDefinition = "boolean default false")
    private Boolean isPlexiglas;
}
