package com.example.WebWarehouse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cell")
public class Cell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cell_name;
    private int capacity;

    @ManyToOne
    @JoinColumn(name = "id_warehouse")
    private Warehouse warehouse;

    @OneToOne
    @JoinColumn(name = "id_product")
    private Product product;

    private int product_quantity;
    public Cell(String cell_name, int capacity, Warehouse warehouse){
        this.cell_name = cell_name;
        this.capacity=capacity;
        this.warehouse=warehouse;
    }
}
