package com.example.WebWarehouse.controller;

import com.example.WebWarehouse.entity.Cell;
import com.example.WebWarehouse.entity.CellProduct;
import com.example.WebWarehouse.entity.User;
import com.example.WebWarehouse.entity.Warehouse;
import com.example.WebWarehouse.services.CellProductService;
import com.example.WebWarehouse.services.CellService;
import com.example.WebWarehouse.services.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("warehouse")
public class WareHouseController {
    private final WarehouseService warehouseService;
    private final CellService cellService;
    private final CellProductService cellProductService;

    @PostMapping("create")
    public String addWareHouse(Warehouse warehouse,@AuthenticationPrincipal User user) {
        warehouseService.save(warehouse,user);
        cellService.fabricSave(warehouse);
        return "redirect:warehouse-index";
    }
    @GetMapping("warehouse-index")
    public String showWarehouseForm(Model model,@AuthenticationPrincipal User user ) {
        List<Warehouse> warehouseList = warehouseService.getWareHouseByUserId(user.getId());
        model.addAttribute("warehouseList", warehouseList);
        return "warehouse-index";
    }
    @GetMapping("delete/{warehouse_id}")
    public String wareHouseDelete(@PathVariable("warehouse_id") Long warehouse_id) {
        warehouseService.delete(warehouse_id);
        return "redirect:/warehouse/warehouse-index";
    }
    @GetMapping("info/{warehouse_id}")
    public String wareHouseInfo(Model model, @PathVariable("warehouse_id") Long warehouse_id)
    {
        Warehouse warehouse = warehouseService.getWarehouseById(warehouse_id);
        List<Cell> cells = cellService.findAllByWarehouseId(warehouse_id);
        /*List<CellProduct> cellProducts.getAll();*/
        model.addAttribute("cells",cells);
        model.addAttribute("warehouse", warehouse);
        return "/warehouse-info";
    }
}
