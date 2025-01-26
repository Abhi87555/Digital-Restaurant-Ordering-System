package com.jack.sparrow.potc.restaurantmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "rm_table")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RmTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tableId;

    @Column(name = "table_number", nullable = false, unique = true)
    private String tableNumber;

    @Column(name = "table_status")
    private String tableStatus;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;
}
