package com.example.WebWarehouse.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double cost;
    private String size;
    private String postman;
    private int quantity;
    @OneToOne
    @JoinColumn(name = "id_user")
    private User user;

}