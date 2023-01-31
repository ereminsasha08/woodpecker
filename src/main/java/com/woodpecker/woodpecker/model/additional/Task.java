package com.woodpecker.woodpecker.model.additional;

import com.woodpecker.woodpecker.model.abstractentity.BaseEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
