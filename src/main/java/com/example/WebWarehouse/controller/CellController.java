package com.example.WebWarehouse.controller;

import com.example.WebWarehouse.entity.Cell;
import com.example.WebWarehouse.entity.CellProduct;
import com.example.WebWarehouse.entity.Product;
import com.example.WebWarehouse.entity.Warehouse;
import com.example.WebWarehouse.services.CellProductService;
import com.example.WebWarehouse.services.CellService;
import com.example.WebWarehouse.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("cell")
public class CellController {
    private final CellService cellService;
    private final CellProductService cellProductService;
    private final ProductService productService;
    @PostMapping("createCells")
    public String addCell(Warehouse warehouse) {
        cellService.fabricSave(warehouse);
        return "contact";
    }
    @PostMapping("setProduct")
    public String setProduct(Product product, Cell cell){
        return "contact";
    }
    @GetMapping("update/{cell_id}")
    public String updateCell(@PathVariable("cell_id") Long cell_id, Model model){
        List<CellProduct> cellProducts = cellProductService.findAllByCellId(cell_id);
        Cell cell = cellService.findById(cell_id);
        List<Product> products = productService.AllProduct();
        model.addAttribute("cell", cell);
        model.addAttribute("cellProductList", cellProducts);
        model.addAttribute("products", products);
        return "cell-update";
    }
}
