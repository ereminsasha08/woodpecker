package org.woodpecker.repository.model.goods;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.woodpecker.repository.model.goods.map.LanguageType;
import org.woodpecker.repository.model.goods.map.LightType;

@MappedSuperclass
@Getter
@Setter
public abstract class Map extends Good {
    @NotNull
    @Column(name = "type_map")
    @Enumerated(EnumType.STRING)
    protected TypeMap typeMap;
    @Column(name = "language", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    protected LanguageType languageType;
    @Column(name = "is_multi_level", nullable = false)
    @NotNull
    protected Boolean isMultiLevel;
    @Column(name = "color", nullable = false)
    @NotNull
    protected String color;
    @Column(name = "is_monochromatic", columnDefinition = "boolean default false")
    protected Boolean isMonochromatic;
    @Column(name = "is_color_plywood")
    protected Boolean isColorPlywood;
    @Column(name = "is_plexiglas", columnDefinition = "boolean default false")
    protected Boolean isPlexiglas;
    @Column(name = "light", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    protected LightType light;
}
