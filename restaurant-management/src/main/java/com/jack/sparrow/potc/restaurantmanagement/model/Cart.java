package com.jack.sparrow.potc.restaurantmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "rm_cart")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @Column(name = "total_item", nullable = false)
    private Integer totalItem;

    @Column(name = "cart_value", nullable = false)
    private Double cartValue;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private RmTable table;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private Set<CartItem> cartItems;

    public Cart(RmTable table, Set<CartItem> cartItems){
        this.table = table;
        this.cartItems = cartItems;
    }
}
