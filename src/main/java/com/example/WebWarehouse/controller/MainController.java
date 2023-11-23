package com.example.WebWarehouse.controller;

import com.example.WebWarehouse.entity.Cell;
import com.example.WebWarehouse.entity.Product;
import com.example.WebWarehouse.entity.User;
import com.example.WebWarehouse.services.CellService;
import com.example.WebWarehouse.services.ProductService;
import com.example.WebWarehouse.services.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("")
public class MainController {
    private final ProductService productService;
    private final CellService cellService;
    private final WarehouseService warehouseService;
    @GetMapping("/about")
    public String openAbout(){
        return "test";
    }
    @GetMapping("/contact")
    public String openContact(){
        return "contact";
    }
    @GetMapping("/")
    public String openHome(){
        return "index";
    }
    @GetMapping("/services")
    public String openServices(){
        return "services";
    }
    @GetMapping("/pricing")
    public String openPricing(){
        return "pricing";
    }
    @GetMapping("/registration")
    public String openRegistration(){
        return "registration";
    }
}
