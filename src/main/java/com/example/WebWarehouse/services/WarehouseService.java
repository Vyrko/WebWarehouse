package com.example.WebWarehouse.services;

import com.example.WebWarehouse.entity.User;
import com.example.WebWarehouse.entity.Warehouse;
import com.example.WebWarehouse.entity.WarehouseWorkerLink;
import com.example.WebWarehouse.repository.WareHouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseService {
    private final WareHouseRepository wareHouseRepository;
    private final CellProductService cellProductService;
    private final WarehouseWorkerLinkService warehouseWorkerLinkService;
    private final UserService userService;

    public void save(Warehouse warehouse, User user) {
        warehouse.setUser(user);
        wareHouseRepository.save(warehouse);
        warehouseWorkerLinkService.addAdmin(user, warehouse);
        userService.setBusy(true,user);
    }

    public List<Warehouse> getWareHouseByUserId(Long id) { return wareHouseRepository.findAllByUserId(id);}
    public List<Warehouse> findByWorkerId(Long id) {
        List<WarehouseWorkerLink> workerLinks = warehouseWorkerLinkService.findWorkerLinkByUserId(id);
        List<Warehouse> warehouses = new ArrayList<>();
        for (int i = 0; i< workerLinks.size(); i++){
           warehouses.add(workerLinks.get(i).getWarehouse());
        }
        return warehouses;
    }

    public Warehouse getWarehouseById(Long id){ return wareHouseRepository.findById(id).orElse(null);}

    @Transactional
    public void delete(Long warehouseId) {
        warehouseWorkerLinkService.deleteByWarehouse(warehouseId);
        deleteAllCellProduct(warehouseId);
        wareHouseRepository.deleteById(warehouseId);
    }
    private void deleteAllCellProduct(Long warehouseId){
        cellProductService.deleteAll(warehouseId);
    }
}
