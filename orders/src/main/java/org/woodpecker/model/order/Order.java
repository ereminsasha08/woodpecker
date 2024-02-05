package org.woodpecker.model.order;

import org.woodpecker.model.map.Stage;
import org.woodpecker.HasId;
import org.woodpecker.model.map.GeographyMap;
import org.woodpecker.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders", schema = "public")
@NoArgsConstructor
@Getter
@Setter
public class Order extends RepresentationModel<Order> implements HasId {

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
//    @OneToMany
//    @JoinColumn(name = "goods_id")
//    private List<OrderItems> goods;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @Fetch(FetchMode.JOIN)
    private User manager;
//    @OneToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "client_id", nullable = false)
//    @Fetch(FetchMode.JOIN)
//    private Client client;


    public Order(LocalDateTime dateCreate, GeographyMap geographyMap, Boolean marketPlace, Stage stage) {
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