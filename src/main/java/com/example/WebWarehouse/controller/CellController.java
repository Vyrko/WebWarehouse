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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @GetMapping("info/{cell_id}")
    public String updateCellForm(@PathVariable("cell_id") Long cell_id, Model model){
        List<CellProduct> cellProducts = cellProductService.findAllByCellId(cell_id);
        Cell cell = cellService.findById(cell_id);
        List<Product> products = productService.AllProduct();
        model.addAttribute("cell", cell);
        model.addAttribute("cellProductList", cellProducts);
        model.addAttribute("products", products);
        String message = (String) model.asMap().get("message");
        if (message != null) {
            model.addAttribute("message", message);
        }
        return "cell-info";
    }
    @PostMapping("/update")
    public String updateCellAddCellProduct(
            @RequestParam("productId") Long productId,
            @RequestParam("quantity") int quantity,
            @RequestParam("cell_id") Long cellId,
            RedirectAttributes redirectAttributes) {
        if (cellProductService.save(cellId, productId, quantity)) {
            cellService.updateCapacity(cellId,productId,quantity);
            redirectAttributes.addFlashAttribute("message", "Товар добавлен в ячейку");
        } else {
            redirectAttributes.addFlashAttribute("message", "Нехватает места на складе");
        }
        return "redirect:/cell/info/" + cellId;
    }
    @GetMapping("cellProduct/{cellProductId}")
    public String updateCellProduct(@PathVariable("cellProductId") Long cellProductId, Model model){
        CellProduct cellProduct = cellProductService.getById(cellProductId);
        model.addAttribute("cellProduct", cellProduct);
        return "cellProduct-update";
    }
    @PostMapping("/cellProduct/update")
    public String updateCellUpdateCellProduct(@ModelAttribute("cellProduct") CellProduct cellProduct) {
        cellProductService.updateQuantity(cellProduct);
        return "redirect:/cell/info/" + cellProduct.getCell().getId();
    }
}