package com.example.WebWarehouse.controller;

import com.example.WebWarehouse.services.CellService;
import com.example.WebWarehouse.services.ProductService;
import com.example.WebWarehouse.services.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @GetMapping("/login")
    public String login() {return "login";}
}
