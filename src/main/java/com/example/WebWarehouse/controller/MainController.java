package com.example.WebWarehouse.controller;

import com.example.WebWarehouse.entity.User;
import com.example.WebWarehouse.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequiredArgsConstructor
@RequestMapping("")
public class MainController {
    private final WarehouseService warehouseService;
    private final OrderService orderService;

    @GetMapping("/about")
    public String openAbout() {
        return "test";
    }

    @GetMapping("/contact")
    public String openContact() {
        return "contact";
    }

    @GetMapping("/")
    public String openHome() {
        return "index";
    }

    @GetMapping("/services")
    public String openServices() {
        return "services";
    }

    @GetMapping("/pricing")
    public String openPricing() {
        return "pricing";
    }

    @GetMapping("/registration")
    public String openRegistration() {
        return "registration";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String handleNotFound(Model model) {    model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        return "error";}

    @GetMapping("/user-info")
    public String info(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("warehouses", warehouseService.findByWorkerId(user.getId()));
        model.addAttribute("orders", orderService.barChar(user));
        return "user-index";
    }
}
