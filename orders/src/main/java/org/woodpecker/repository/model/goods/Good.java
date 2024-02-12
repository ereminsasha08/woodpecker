package org.woodpecker.repository.model.goods;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.woodpecker.repository.model.HasId;

import java.time.LocalDateTime;

@Entity
@Table(name = "good", schema = "goods")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
public class Good implements HasId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    @Column(name = "name")
    protected String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "type_goods")
    protected TypeGoods typeGoods;
    @Embedded
    protected Size size;
    @NotNull
    protected Integer price;
    protected String description;
    protected LocalDateTime dateCreate;
//    @NotNull
//    @ManyToOne
//    protected User userCreate;
    @Column(name = "is_view", columnDefinition = "boolean default true")
    @JsonIgnore
    protected boolean isView;
}
