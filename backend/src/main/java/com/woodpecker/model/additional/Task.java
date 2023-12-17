package com.woodpecker.model.additional;

import com.woodpecker.model.abstractentity.BaseEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "task")
@RequiredArgsConstructor
@Getter
@Setter
public class Task extends BaseEntity {
    @Column(name = "target")
    private String target;

    @Column(name = "number")
    private Integer number;

    @Column(name = "map")
    private String map;

    @Column(name = "comment")
    private String comment;


}
