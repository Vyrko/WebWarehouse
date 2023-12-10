package com.example.WebWarehouse.model;

import com.example.WebWarehouse.entity.User;
import lombok.Data;

import java.util.List;
@Data
public class OrderFormModel {
    private Long supplier;
    private Long buyer;
    private List<Long> productIds;
    private List<Integer> quantities;
    private double cost;
    private Long supplierWarehouseId;

    public OrderFormModel(Long id, Long supplierId) {
        this.buyer=id;
        this.supplier=supplierId;
    }
}
