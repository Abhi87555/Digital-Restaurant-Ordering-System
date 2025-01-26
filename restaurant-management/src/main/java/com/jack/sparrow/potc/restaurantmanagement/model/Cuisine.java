package com.jack.sparrow.potc.restaurantmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "rm_cuisine")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cuisine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cuisine_id")
    private Long cuisineId;

    @Column(name = "cuisine_name", nullable = false, unique = true)
    private String cuisineName;

    @Column(name = "cuisine_description")
    private String cuisineDescription;

    @Column(name = "cuisine_type", nullable = false)
    private String cuisineType;

    public Cuisine(String cuisineName, String cuisineDescription, String cuisineType) {
        this.cuisineName = cuisineName;
        this.cuisineDescription = cuisineDescription;
        this.cuisineType = cuisineType;
    }
}

