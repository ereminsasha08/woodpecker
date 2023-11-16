package com.woodpecker.woodpecker.model.map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@Getter
@Setter

public class OrderMap implements Serializable {

    @Id
    private Integer id;
    @NotNull
    @Column(name = "order_term")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime orderTerm;

    @NotNull
    @Column(name = "completed")
    private Boolean completed;
    @Column(name = "is_color_plywood")
    private Boolean isColorPlywood;
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    @NotNull
    @JsonIgnoreProperties("orderMap")
    private GeographyMap geographyMap;


    @Column(name = "cut_begin")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime cutBegin;


    @Column(name = "cut_end")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime cutEnd;
    @Column(name = "painting_begin")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime paintingBegin;

    @Column(name = "painting_end")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime paintingEnd;

    @Column(name = "gluing_end")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime gluingEnd;


    @Column(name = "packed_end")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime packedEnd;

    @Column(name = "post_end")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime postEnd;

    @Column(name = "laser")
    private String laser;

    @Column(name = "market_place")
    private Boolean marketPlace;

    @ElementCollection
    @CollectionTable(name = "plywood_list_for_map")
    private List<String> plywoodList;

    @Column(name = "painter")
    private String namePainter;

    @Column(name = "stage", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private Stage stage;

    @Column(name = "availability")
    private Boolean isAvailability;

    @Column(name = "is_paid", columnDefinition = "boolean default false")
    private Boolean isPaid;

    public OrderMap(LocalDateTime orderTerm, GeographyMap geographyMap, Boolean marketPlace, Stage stage, Boolean isAvailability) {
        this.isPaid = marketPlace;
        this.orderTerm = orderTerm;
        this.geographyMap = geographyMap;
        this.marketPlace = marketPlace;
        this.stage = stage;
        this.isAvailability = isAvailability;
        this.completed = false;
    }

    public void setAllTime() {
        cutBegin = orderTerm;
        cutEnd = orderTerm;
        paintingBegin = orderTerm;
        gluingEnd = orderTerm;
        packedEnd = orderTerm;
    }
}