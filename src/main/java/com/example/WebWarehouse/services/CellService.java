package com.example.WebWarehouse.services;

import com.example.WebWarehouse.entity.Cell;
import com.example.WebWarehouse.entity.Product;
import com.example.WebWarehouse.entity.Warehouse;
import com.example.WebWarehouse.repository.CellRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CellService {
    public final CellRepository cellRepository;
    private final WarehouseService warehouseService;
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

    public void setProduct(Product product, Cell cell) {

    }
}
