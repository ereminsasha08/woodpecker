package com.woodpecker.woodpecker.model.order;

import com.woodpecker.woodpecker.HasId;
import com.woodpecker.woodpecker.model.map.GeographyMap;
import com.woodpecker.woodpecker.model.map.Stage;
import com.woodpecker.woodpecker.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders", schema = "public")
@NoArgsConstructor
@Getter
@Setter
public class Order implements HasId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Column(name = "date_create")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateCreate;
    @NotNull
    @Column(name = "completed")
    private Boolean completed;
    @Column(name = "market_place")
    private Boolean marketPlace;
    @Column(name = "is_paid", columnDefinition = "boolean default false")
    private Boolean isPaid;
    @OneToMany
    @JoinColumn(name = "order_id")
    private List<GeographyMap> geographyMap;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @Fetch(FetchMode.JOIN)
    private User manager;

    public Order(LocalDateTime dateCreate, GeographyMap geographyMap, Boolean marketPlace, Stage stage, Boolean isAvailability) {
        this.isPaid = marketPlace;
        this.dateCreate = dateCreate;
        this.marketPlace = marketPlace;
        this.completed = false;
//        this.geographyMap = geographyMap;
//        this.stage = stage;
//        this.isAvailability = isAvailability;
    }

    public void setAllTime() {
//        cutBegin = dateCreate;
//        cutEnd = dateCreate;
//        paintingBegin = dateCreate;
//        gluingEnd = dateCreate;
//        packedEnd = dateCreate;
    }

}