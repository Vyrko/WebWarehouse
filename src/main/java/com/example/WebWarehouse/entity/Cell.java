package com.example.WebWarehouse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cell")
public class Cell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cell_name;
    private double capacity;

    @ManyToOne
    @JoinColumn(name = "id_warehouse")
    private Warehouse warehouse;

    public Cell(String cell_name, double capacity, Warehouse warehouse){
        this.cell_name=cell_name;
        this.capacity=capacity;
        this.warehouse=warehouse;
    }
    public boolean checkCapacity(double inputCapacity) {return inputCapacity <= capacity; }
}
