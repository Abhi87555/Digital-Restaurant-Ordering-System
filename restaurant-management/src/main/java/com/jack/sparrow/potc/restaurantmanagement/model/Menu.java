package com.jack.sparrow.potc.restaurantmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "rm_menu")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @ManyToOne
    @JoinColumn(name = "cuisine_id", nullable = false)
    private Cuisine cuisine;

    @Column(name = "portion")
    private String portion;

    @Column(name = "price", nullable = false)
    private Double price;

    public Menu(Cuisine cuisine, String portion, Double price) {
        this.cuisine = cuisine;
        this.portion = portion;
        this.price = price;
    }
}
