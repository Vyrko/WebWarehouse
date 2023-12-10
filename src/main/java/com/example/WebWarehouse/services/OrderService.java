package com.example.WebWarehouse.services;

import com.example.WebWarehouse.entity.CellProduct;
import com.example.WebWarehouse.entity.Order;
import com.example.WebWarehouse.entity.Product;
import com.example.WebWarehouse.entity.User;
import com.example.WebWarehouse.model.OrderFormModel;
import com.example.WebWarehouse.repository.OrderRepository;
import com.example.WebWarehouse.repository.ProductRepository;
import com.example.WebWarehouse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final CellProductService cellProductService;
    /*public void save(User buyer, User supplier, Product product, double cost){
        orderRepository.save(new Order(buyer,supplier,product,cost));
    }*/
    public void saveOrders(OrderFormModel orderFormModel) {
        for(int i=0; i<orderFormModel.getProductIds().size(); i++){
            orderRepository.save(new Order(
                    userRepository.getById(orderFormModel.getBuyer()),
                    userRepository.getById(orderFormModel.getSupplier()),
                    productRepository.findProductById(orderFormModel.getProductIds().get(i)),
                    orderFormModel.getCost(),
                    orderFormModel.getQuantities().get(i)
            ));
        }
    }
    public void updateAfterOrder(OrderFormModel orderFormModel) {
        for (int i = 0; i < orderFormModel.getProductIds().size(); i++) {
            List<CellProduct> cellProductDB = cellProductService.findByProductId(orderFormModel.getProductIds().get(i));
            for (final CellProduct cellProduct : cellProductDB) {
                if (cellProduct.getQuantity() >= orderFormModel.getQuantities().get(i)) {
                    CellProduct newCellProduct = new CellProduct(); // Создать новый объект CellProduct
                    newCellProduct.setId(cellProduct.getId()); // Перенести значения из cellProduct в newCellProduct
                    newCellProduct.setQuantity(orderFormModel.getQuantities().get(i));
                    newCellProduct.setCell(cellProduct.getCell());
                    newCellProduct.setProduct(cellProduct.getProduct());
                    double c = cellProduct.getQuantity();
                    cellProductService.updateQuantity(newCellProduct);
                    break;
                } else {
                    CellProduct newCellProduct = new CellProduct(); // Создать новый объект CellProduct
                    newCellProduct.setId(cellProduct.getId()); // Перенести значения из cellProduct в newCellProduct
                    newCellProduct.setCell(cellProduct.getCell());
                    newCellProduct.setProduct(cellProduct.getProduct());
                    newCellProduct.setQuantity(cellProduct.getQuantity());
                    orderFormModel.getQuantities().set(i, (int) (orderFormModel.getQuantities().get(i) - cellProduct.getQuantity()));
                    cellProductService.updateQuantity(newCellProduct);
                }
            }
        }
    } }



