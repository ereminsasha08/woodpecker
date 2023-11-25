package com.woodpecker.woodpecker.model.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.woodpecker.woodpecker.HasId;
import com.woodpecker.woodpecker.model.map.GeographyMap;
import com.woodpecker.woodpecker.model.map.Stage;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.FetchType.*;
import static org.hibernate.annotations.FetchMode.*;

@Data
@Entity
@Table(name = "geograhy_map_production", schema = "production")
public class GeographyMapProduction implements HasId {
    @Id
    private Integer id;
    @Column(name = "geography_map_id")
    private Integer geographyMapId;

    @Column(name = "is_color_plywood")
    private Boolean isColorPlywood;
    @Column(name = "stage", nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private Stage stage;
    @Column(name = "laser")
    private String laser;
    @ElementCollection
    @CollectionTable(name = "plywood_list_for_map")
    private List<String> plywoodList;
    @Column(name = "painter")
    private String namePainter;
    @Column(name = "availability")
    private Boolean isAvailability;
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
    @Column(name = "gluing_start")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime gluingStart;
    @Column(name = "gluing_end")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime gluingEnd;
    @Column(name = "packed_end")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime packedEnd;
    @Column(name = "post_end")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime postEnd;

}
