package com.example.WebWarehouse.controller;

import com.example.WebWarehouse.entity.Product;
import com.example.WebWarehouse.entity.User;
import com.example.WebWarehouse.entity.Warehouse;
import com.example.WebWarehouse.entity.WarehouseWorkerLink;
import com.example.WebWarehouse.services.UserService;
import com.example.WebWarehouse.services.WarehouseService;
import com.example.WebWarehouse.services.WarehouseWorkerLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class WarehouseWorkerController {
    private final UserService userService;
    private final WarehouseService warehouseService;
    private final WarehouseWorkerLinkService warehouseWorkerLinkService;
    private final List<WarehouseWorkerLink> warehouseWorkerLinks=new ArrayList<>();

    @GetMapping("warehouse-worker")
    public String  warehouseWorkerForm(Model model, @AuthenticationPrincipal User user){
        List<User> users = userService.findAll();
        List<Warehouse> warehouses = warehouseService.getWareHouseByUserId(user.getId());
        WarehouseWorkerLink warehouseWorkerLink = new WarehouseWorkerLink();
        model.addAttribute("users",users);
        model.addAttribute("warehouses",warehouses);
        model.addAttribute("warehouseWorkerLink", warehouseWorkerLink);
        model.addAttribute("warehouseWorkerLinks",warehouseWorkerLinks);
        return "addWorkerForWarehouse";
    }
    @PostMapping("workerLink/save")
    public String saveWorker(@ModelAttribute("warehouseWorkerLink") WarehouseWorkerLink warehouseWorkerLink) {
        warehouseWorkerLinks.add(warehouseWorkerLink);
        return "redirect:/warehouse-worker";
    }
    @PostMapping("workerLink/save-all")
    public String saveAllWorker() {
        if (warehouseWorkerLinks.isEmpty()) {return "redirect:/product/product-form";}
        warehouseWorkerLinkService.saveAllWorker(warehouseWorkerLinks);
        warehouseWorkerLinks.clear();
        return "redirect:/";
    }
}
