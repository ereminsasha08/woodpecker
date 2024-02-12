package org.woodpecker.repository.model.support;

import jakarta.persistence.*;
import lombok.Getter;
import org.woodpecker.repository.model.HasId;

import java.util.List;

@Entity
@Table(name = "plywood_list")
@Getter
public class PlywoodList implements HasId {
    @Id
    private Integer id;

    @ElementCollection
    @CollectionTable(name = "lists")
    @Column(name = "list")
    private List<String> lists;

    @Override
    public void setId(Integer id) {
        throw new UnsupportedOperationException();
    }
}
