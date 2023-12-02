package com.example.WebWarehouse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "cell_product")
public class CellProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cell")
    private Cell cell;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

    private double quantity;

    public CellProduct(Cell cell, Product product, double quantity) {
        this.cell = cell;
        this.product = product;
        this.quantity = quantity;
    }
}
