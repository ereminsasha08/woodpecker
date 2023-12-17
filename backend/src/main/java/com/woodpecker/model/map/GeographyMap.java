package com.woodpecker.model.map;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.woodpecker.model.abstractentity.Product;
import com.woodpecker.model.order.GeographyMapProduction;
import com.woodpecker.model.order.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "maps")
@NoArgsConstructor
public class GeographyMap extends Product {

    @Column(name = "type_map", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    @NotBlank
    private MapType mapType;
    @Column(name = "size", nullable = false)
    @NotNull
    private Integer size;
    @Column(name = "language", nullable = false)
    @NotNull
    @NotBlank
    private LanguageType languageType;
    @Column(name = "is_multi_level", nullable = false)
    @NotNull
    private Boolean isMultiLevel;
    @Column(name = "color", nullable = false)
    @NotNull
    @NotBlank
    private String color;
    @Column(name = "is_state", nullable = false)
    @NotNull
    private Boolean isState;
    @Column(name = "is_capital", nullable = false)
    private boolean isCapital = true;
    @Column(name = "is_monochromatic", columnDefinition = "boolean default false")
    private Boolean isMonochromatic;
    @Column(name = "is_color_plywood")
    private Boolean isColorPlywood;
    @Column(name = "is_plexiglas", columnDefinition = "boolean default false")
    private Boolean isPlexiglas;
    @Column(name = "light", nullable = false)
    @NotNull
    @NotBlank
    private LightType light;
    @Column(name = "additional", length = 800)
    private String additional;
    @OneToOne(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "geography_map_production_id", referencedColumnName = "geography_map_id")
    @JsonIgnoreProperties("geographyMap")
    private GeographyMapProduction geographyMapProduction;
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @JsonIgnore
    private Order order;
    @Column(name = "is_view", columnDefinition = "boolean default true")
    @JsonIgnore
    private boolean isView;


}
