package com.example.WebWarehouse.services;

import com.example.WebWarehouse.entity.User;
import com.example.WebWarehouse.entity.Warehouse;
import com.example.WebWarehouse.repository.WareHouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseService {
    private final WareHouseRepository wareHouseRepository;
    private final CellProductService cellProductService;

    public void save(Warehouse warehouse, User user) {
        warehouse.setUser(user);
        wareHouseRepository.save(warehouse);
    }

    public List<Warehouse> getWareHouseByUserId(Long id) { return wareHouseRepository.findAllByUserId(id);}

    public Warehouse getWarehouseById(Long id){ return wareHouseRepository.findById(id).orElse(null);}

    public void delete(Long warehouseId) {
        deleteAllCellProduct(warehouseId);
        wareHouseRepository.deleteById(warehouseId);
    }
    private void deleteAllCellProduct(Long warehouseId){
        cellProductService.deleteAll(warehouseId);
    }
}
