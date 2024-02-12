package org.woodpecker.repository.model.goods;

import lombok.Data;

import jakarta.persistence.*;

@Entity
@Table(name = "map_country", schema = "goods")
@Data
public class CountryMap extends Map {

}
