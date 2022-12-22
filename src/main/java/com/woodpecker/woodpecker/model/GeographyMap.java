package com.woodpecker.woodpecker.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "maps")
public class GeographyMap extends Product {

    @Column(name = "type_map", nullable = false)
    @NotNull
    private String typeMap;

    @Column(name = "condition_map", nullable = false)
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
    @Size(min = 1, max = 500)
    private String description;

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
//    @NotNull
    private User user;

    @Column(name = "is_state", nullable = false)
    @NotNull
    private boolean isState;

    @Column(name = "light", nullable = false)
    @NotNull
    private String light;


    @Column(name = "additional", nullable = false)
    @NotNull
    private String additional;


    public GeographyMap() {
    }

    public GeographyMap(String typeMap, int conditionMap, int size,
                        String language, boolean isMultiLevel, boolean isState,
                        String color, String description, LocalDateTime dateTime) {
        this.typeMap = typeMap;
        this.conditionMap = conditionMap;
        this.size = size;
        this.language = language;
        this.isState = isState;
        this.isMultiLevel = isMultiLevel;
        this.color = color;
        this.description = description;
        this.dateTime = dateTime;
    }

    public GeographyMap(String typeMap, LocalDateTime dateTime) {
        this.typeMap = typeMap;

        this.dateTime = dateTime;
    }


}
