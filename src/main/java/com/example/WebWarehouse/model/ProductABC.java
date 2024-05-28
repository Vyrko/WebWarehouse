package com.example.WebWarehouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductABC {
    private String name;
    private double revenue;
    private String status;
}
