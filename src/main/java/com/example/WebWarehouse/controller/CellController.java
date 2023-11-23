package com.example.WebWarehouse.controller;

import com.example.WebWarehouse.entity.Cell;
import com.example.WebWarehouse.entity.Product;
import com.example.WebWarehouse.entity.Warehouse;
import com.example.WebWarehouse.services.CellService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("cell")
public class CellController {
    private final CellService cellService;
    @PostMapping("createCells")
    public String addCell(Warehouse warehouse) {
        cellService.fabricSave(warehouse);
        return "contact";
    }
    @PostMapping("setProduct")
    public String setProduct(Product product, Cell cell){
        cellService.setProduct(product,cell);
        return "contact";
    }

}
