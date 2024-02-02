package org.woodpecker.model.support;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Table(name = "plywood_list")
@Getter
public class PlywoodList {
    @Id
    private Integer id;

    @ElementCollection
    @CollectionTable(name = "lists")
    @Column(name = "list")
    private List<String> lists;
}
