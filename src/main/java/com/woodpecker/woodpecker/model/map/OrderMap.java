package com.woodpecker.woodpecker.model.map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@Getter
@Setter

public class OrderMap {

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
    private LocalDateTime cut_begin;


    @Column(name = "cut_end")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime cut_end;
    @Column(name = "painting_begin")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime painting_begin;

    @Column(name = "painting_end")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime painting_end;

    @Column(name = "gluing_end")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime gluing_end;


    @Column(name = "packed_end")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime packed_end;

    @Column(name = "post_end")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime post_end;

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
    private Integer stage;

    @Column(name = "availability")
    private Boolean isAvailability;

    @Column(name = "is_paid", columnDefinition = "boolean default false")
    private Boolean isPaid;

    public OrderMap(LocalDateTime orderTerm, GeographyMap geographyMap, Boolean marketPlace, Integer stage, Boolean isAvailability) {
        this.isPaid = marketPlace;
        this.orderTerm = orderTerm;
        this.geographyMap = geographyMap;
        this.marketPlace = marketPlace;
        this.stage = stage;
        this.isAvailability = isAvailability;
        this.completed = false;
    }

    public void setAllTime() {
        cut_begin = orderTerm;
        cut_end = orderTerm;
        painting_begin = orderTerm;
        gluing_end = orderTerm;
        packed_end = orderTerm;
    }
}