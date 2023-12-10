package com.example.WebWarehouse.services;

import com.example.WebWarehouse.entity.Cell;
import com.example.WebWarehouse.entity.CellProduct;
import com.example.WebWarehouse.entity.Product;
import com.example.WebWarehouse.model.OrderFormModel;
import com.example.WebWarehouse.repository.CellProductRepository;
import com.example.WebWarehouse.repository.CellRepository;
import com.example.WebWarehouse.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.PushBuilder;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CellProductService {
    private final ProductService productService;
    private final CellRepository cellRepository;
    private final ProductRepository productRepository;
    private final CellProductRepository cellProductRepository;
    private final CellService cellService;

    public boolean save(Long cell_id, Long product_id, double quantity) {
        if (checkCapacityCell(cell_id, quantity) && checkQuantityProduct(product_id, (int) quantity)) {
            productService.decreaseQuantity((int) quantity, product_id);
            CellProduct cellProduct = duplicateRecordCheck(cell_id, product_id, quantity);
            cellProductRepository.save(cellProduct);
            return true;
        } else {
            return false;
        }
    }

    public List<CellProduct> findAllByCellId(Long cell_id) {
        return cellProductRepository.findAllByCell_Id(cell_id);
    }

    private CellProduct duplicateRecordCheck(Long cell_id, Long product_id, double quantity) {
        Cell cell = cellRepository.findCellById(cell_id);
        Product product = productRepository.findProductById(product_id);
        CellProduct newCellProduct = new CellProduct(cell, product, quantity);
        CellProduct cellProductFromDB = cellProductRepository.getByCell_IdAndProductId(cell_id, product_id);
        if (cellProductFromDB == null) {
            return newCellProduct;
        } else {
            cellProductFromDB.setQuantity(cellProductFromDB.getQuantity() + quantity);
            return cellProductFromDB;
        }
    }

    private boolean checkCapacityCell(Long cell_id, double quantity) {
        Cell cell = cellRepository.findCellById(cell_id);
        return cell.checkCapacity(quantity);
    }

    private boolean checkQuantityProduct(Long productId, int quantity) {
        return productRepository.findProductById(productId).getQuantity() >= quantity;
    }

    public List<CellProduct> getAll(Long cell_id) {
        return cellProductRepository.findAllByCell_Id(cell_id);
    }

    public CellProduct getById(Long cellProductId) {
        return cellProductRepository.getById(cellProductId);
    }

    public void updateQuantity(CellProduct cellProduct) {

        CellProduct cellProductFromDB = cellProductRepository.getById(cellProduct.getId());
        double a = cellProduct.getQuantity();
        double b = cellProductFromDB.getQuantity();
        cellProductFromDB.setQuantity(cellProductFromDB.getQuantity() - cellProduct.getQuantity());
        int o = (int) cellProductFromDB.getQuantity();
        setCellCapacity(cellProductFromDB.getCell().getId(),
                cellProductFromDB.getProduct().getId(),
                (int) cellProduct.getQuantity() * -1);

        if (cellProductFromDB.getQuantity() == 0) {
            cellProductRepository.deleteById(cellProduct.getId());
        } else {
            cellProductRepository.save(cellProductFromDB);
        }

    }

    public void setCellCapacity(Long cellId, Long productId, int quantity) {
        cellService.updateCapacity(cellId, productId, quantity);
    }

    public List<CellProduct> findByProductId(Long productId) {
        return cellProductRepository.findByProductId(productId);
    }

    public void deleteByCellId(Long cellId) {
        cellProductRepository.deleteByCell_Id(cellId);
    }

    @Transactional
    public void deleteAll(Long warehouseId) {
        List<Cell> cells = cellService.findAllByWarehouseId(warehouseId);
        for (int i = 0; i < cells.size(); i++) {
            deleteByCellId(cells.get(i).getId());
        }
    }

    @Transactional
    public void deleteByProductId(Long productId) {
        for (CellProduct cellProduct : findByProductId(productId)) {
            setCellCapacity(cellProduct.getCell().getId(),
                    cellProduct.getProduct().getId(),
                    (int) cellProduct.getQuantity() * -1);
            cellProductRepository.deleteByProductId(productId);
        }
    }

}
