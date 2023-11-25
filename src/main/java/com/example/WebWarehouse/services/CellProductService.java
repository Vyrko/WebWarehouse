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

    public void save(Long cell_id, Long product_id, int quantity){
        Cell cell= cellRepository.findCellById(cell_id);
        Product product = productRepository.findProductById(product_id);
        CellProduct cellProduct= new CellProduct(cell,product,quantity);
        cellProductRepository.save(cellProduct);
    }
    public List<CellProduct> findAllByCellId(Long cell_id){
        return cellProductRepository.findAllByCell_Id(cell_id);
    }
    public List<CellProduct> getAll(Long cell_id){
        return cellProductRepository.findAllByCell_Id(cell_id);
    }
}
