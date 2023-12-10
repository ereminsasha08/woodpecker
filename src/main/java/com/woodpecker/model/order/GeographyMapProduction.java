package com.woodpecker.model.order;

import com.woodpecker.model.map.Stage;
import com.woodpecker.HasId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "geography_map_production", schema = "production")
public class GeographyMapProduction implements HasId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "geography_map_id", nullable = false)
    private Integer geographyMapId;
    @Column(name = "is_painted", nullable = false)
    private Boolean isPainted;
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
    private String painter;
    @Column(name = "is_availability")
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
    @Column(name = "gluing_begin")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime gluingBegin;
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
