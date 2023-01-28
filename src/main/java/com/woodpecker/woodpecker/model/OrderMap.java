package com.woodpecker.woodpecker.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
    private boolean completed;

    @OneToOne
    @JoinColumn(name = "id")
    @NotNull
    @MapsId
    private GeographyMap geographyMap;

    public OrderMap(LocalDateTime orderTerm, GeographyMap geographyMap) {
        this.orderTerm = orderTerm;
        this.geographyMap = geographyMap;
        this.completed = false;
    }
}