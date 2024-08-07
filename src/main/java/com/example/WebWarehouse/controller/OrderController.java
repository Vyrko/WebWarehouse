package com.example.WebWarehouse.controller;

import com.example.WebWarehouse.entity.Cell;
import com.example.WebWarehouse.entity.Order;
import com.example.WebWarehouse.entity.User;
import com.example.WebWarehouse.model.CharOrder;
import com.example.WebWarehouse.model.OrderFormModel;
import com.example.WebWarehouse.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("order")
public class OrderController {
    private final WarehouseService warehouseService;
    private final OrderService orderService;
    private final ProductService productService;
    private final UserService userService;
    private final CellProductService cellProductService;
    private final CellService cellService;
        @GetMapping("/")
        @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String productMain(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("activeUser", user);
        return "choosing-supplier";
    }
    @GetMapping("/{supplierId}")
    public String openForm(@PathVariable("supplierId") Long supplierId,
                           @AuthenticationPrincipal User buyer, Model model){
        model.addAttribute("orderModel", new OrderFormModel(buyer.getId(),supplierId));
        model.addAttribute("products", productService.getAllProductByUserId(supplierId));
        return "order-index";
    }
    @GetMapping("/confirmation")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String confirmation(OrderFormModel orderFormModel, Model model) {
        model.addAttribute("supplierWarehouse", warehouseService.getWareHouseByUserId(orderFormModel.getSupplier()).get(0).getLocation());
        model.addAttribute("buyerWarehouse", warehouseService.getWareHouseByUserId(orderFormModel.getBuyer()).get(0).getLocation());
        model.addAttribute("orderModel", orderFormModel);
        model.addAttribute("totalCost", orderService.calculateCost(orderFormModel));
        return "order-confirmation";
    }
    @PostMapping("/saveOrders")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String saveOrders(OrderFormModel orderFormModel) {
        final List<Long> saveProduct = new ArrayList<>(orderFormModel.getProductIds());
        orderService.saveOrders(orderFormModel);
        productService.updateAfterOrder(orderFormModel);
        orderFormModel.setProductIds(saveProduct);
        orderService.updateAfterOrder(orderFormModel);
        return "index";
    }
    @GetMapping("/chart")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String chart(@AuthenticationPrincipal User user,Model model) {
        model.addAttribute("chart", orderService.getChart(user));
        return "maping";
    }
    @GetMapping("/bar-cost")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String barChart(@AuthenticationPrincipal User user,Model model) {
        model.addAttribute("bar", orderService.barChar(user) );
        model.addAttribute("supplier", orderService.findBySupplier(user) );
        return "bar-cost";
    }
}
