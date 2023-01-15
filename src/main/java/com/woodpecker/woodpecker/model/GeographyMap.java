package com.woodpecker.woodpecker.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "maps")
@NoArgsConstructor
public class GeographyMap extends Product {

    @Column(name = "type_map", nullable = false)
    @NotNull
    private String typeMap;
    @Column(name = "condition_map")
    @NotNull
    private int conditionMap;

    @Column(name = "size", nullable = false)
    @NotNull
    private int size;

    @Column(name = "language_map", nullable = false)
    @NotNull
    private String language;

    @Column(name = "is_multi_level", nullable = false)
    @NotNull
    private boolean isMultiLevel;

    @Column(name = "color", nullable = false)
    @NotNull
    private String color;

    @Column(name = "description", nullable = false)
    @NotBlank
    private String description;

    @Column(name = "date_time", nullable = false)
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateTime;

    @Column(name = "is_state", nullable = false)
    @NotNull
    private boolean isState;

    @Column(name = "light", nullable = false)
    @NotNull
    private String light;

    @Column(name = "additional", nullable = false)
    private String additional;

}
