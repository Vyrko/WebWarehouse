package com.example.WebWarehouse.services;

import com.example.WebWarehouse.entity.Cell;
import com.example.WebWarehouse.entity.Product;
import com.example.WebWarehouse.entity.Warehouse;
import com.example.WebWarehouse.repository.CellRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class CellService {
    public final CellRepository cellRepository;
    private final ProductService productService;
    public void save(Cell cell){
        cellRepository.save(cell);
    }
    public int calculateCellCount(double capacity){
        return (int) Math.ceil(capacity/10.0);
    }
    public void fabricSave(Warehouse warehouse){
        int cellCount=calculateCellCount(warehouse.getCapacity());
        for (int i=0; i<cellCount;i++){
            if (i<10){
                Cell cell=new Cell("A"+i,10,warehouse);
                save(cell);
            }
            if (i>200){
                Cell cell=new Cell("B"+i,10, warehouse);
                save(cell);
            }
        }
    }
    public List<Cell> findAllByWarehouseId(Long id){
        return cellRepository.findAllByWarehouseId(id);
    }
    public void updateCapacity(Long cellId, Long productId, int quantity){
        Product product = productService.findById(productId);
        DecimalFormat decimalFormat = new DecimalFormat("#0.0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        Cell cellFromDB = cellRepository.findCellById(cellId);
        double result = Double.parseDouble(decimalFormat.format(cellFromDB.getCapacity() - product.getSize() * quantity));
        cellFromDB.setCapacity(result);
        cellRepository.save(cellFromDB);
    }
    public Cell findById(Long cellId) {return cellRepository.findCellById(cellId); }

}
