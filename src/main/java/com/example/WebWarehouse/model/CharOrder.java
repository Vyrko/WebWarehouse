package com.example.WebWarehouse.model;

import com.example.WebWarehouse.entity.Product;
import com.example.WebWarehouse.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data

public class CharOrder {
    private List<Product> products = new ArrayList<>();
    private List<Integer> quantity= new ArrayList<>();
    private List<String> localDateTime = new ArrayList<>();
    private User user;

}
