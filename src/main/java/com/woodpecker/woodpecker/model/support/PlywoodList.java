package com.woodpecker.woodpecker.model.support;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "plywood_list")
@Getter
public class PlywoodList {

    @Id
    private Integer id;

    @ElementCollection
    @CollectionTable(name = "list")
    private List<String> lists;
}
