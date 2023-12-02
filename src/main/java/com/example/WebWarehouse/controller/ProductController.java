package com.example.WebWarehouse.controller;

import com.example.WebWarehouse.entity.Product;
import com.example.WebWarehouse.entity.User;
import com.example.WebWarehouse.entity.Warehouse;
import com.example.WebWarehouse.services.CellService;
import com.example.WebWarehouse.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("product")
public class ProductController {
    private List<Product> productList = new ArrayList<>();
    private final ProductService productService;
    @GetMapping("/")
    public String productMain(Model model, @AuthenticationPrincipal User user) {
        List<Product> products = productService.getAllProductByUserId(user.getId());
        model.addAttribute("products", products);
        return "product-index";
    }
    @PostMapping("save-product")
    public String saveProduct(@ModelAttribute("product") Product product) {
        productList.add(product);
        return "redirect:/product/product-form";
    }

    @GetMapping("product-form")
    public String showProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        model.addAttribute("productList", productList);
        return "product-form";
    }

    @PostMapping("save-all-products")
    public String saveAllProducts(@AuthenticationPrincipal User user) {
        if (productList.isEmpty()) {return "redirect:/product/product-form";}
        productService.saveAllProducts(productList,user);
        productList.clear();
        return "redirect:/product/product-form";
    }
}
