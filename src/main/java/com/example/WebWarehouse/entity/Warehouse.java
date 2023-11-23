package com.example.WebWarehouse.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "warehouse")
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;
    private double capacity;
    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.REMOVE)
    private List<Cell> cellList =new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "id_user")
    private User user;
}
