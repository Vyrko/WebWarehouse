package com.example.WebWarehouse.services;

import com.example.WebWarehouse.entity.Cell;
import com.example.WebWarehouse.entity.CellProduct;
import com.example.WebWarehouse.entity.Product;
import com.example.WebWarehouse.repository.CellProductRepository;
import com.example.WebWarehouse.repository.CellRepository;
import com.example.WebWarehouse.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CellProductService {
    private final CellRepository cellRepository;
    private final ProductRepository productRepository;
    private final CellProductRepository cellProductRepository;
    private final CellService cellService;

    public boolean save(Long cell_id, Long product_id, double quantity){
        if (checkCapacityCell(cell_id,quantity)){
            CellProduct cellProduct = duplicateRecordCheck(cell_id,product_id,quantity);
            cellProductRepository.save(cellProduct);
            return true;
        } else {
            return false;
        }

    }
    public List<CellProduct> findAllByCellId(Long cell_id){
        return cellProductRepository.findAllByCell_Id(cell_id);
    }
    private CellProduct duplicateRecordCheck(Long cell_id, Long product_id, double quantity){
        Cell cell= cellRepository.findCellById(cell_id);
        Product product = productRepository.findProductById(product_id);
        CellProduct newCellProduct= new CellProduct(cell,product,quantity);
        CellProduct cellProductFromDB = cellProductRepository.getByCell_IdAndProductId(cell_id,product_id);
        if (cellProductFromDB == null){
            return newCellProduct;
        } else {
            cellProductFromDB.setQuantity(cellProductFromDB.getQuantity() + quantity);
            return cellProductFromDB;
        }
    }
    private boolean checkCapacityCell(Long cell_id, double quantity){
        Cell cell = cellRepository.findCellById(cell_id);
        return cell.checkCapacity(quantity);
    }
    public List<CellProduct> getAll(Long cell_id){
        return cellProductRepository.findAllByCell_Id(cell_id);
    }

    public CellProduct getById(Long cellProductId) {
        return cellProductRepository.getById(cellProductId);
    }

    public void updateQuantity(CellProduct cellProduct) {
        CellProduct cellProductFromDB= cellProductRepository.getById(cellProduct.getId());
        cellProductFromDB.setQuantity( cellProductFromDB.getQuantity() - cellProduct.getQuantity());
        setCellCapacity(cellProductFromDB.getCell().getId(),
                        cellProductFromDB.getProduct().getId(),
                  (int) cellProduct.getQuantity() * -1);
        cellProductRepository.save(cellProductFromDB);
    }
    public void setCellCapacity(Long cellId,Long productId, int quantity){
        cellService.updateCapacity(cellId,productId,quantity);
    }
}
