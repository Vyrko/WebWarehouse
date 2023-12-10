package com.example.WebWarehouse.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private User supplier;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private int quantity;

    private double cost;
    private LocalDateTime orderDate;

    public Order(User buyer, User supplier, Product product, double cost, int quantity) {
        this.buyer = buyer;
        this.supplier = supplier;
        this.product = product;
        this.cost = cost;
        this.quantity=quantity;
        this.orderDate = LocalDateTime.now();
    }
}
