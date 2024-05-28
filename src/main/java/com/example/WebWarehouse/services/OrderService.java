package com.example.WebWarehouse.services;

import com.example.WebWarehouse.entity.CellProduct;
import com.example.WebWarehouse.entity.Order;
import com.example.WebWarehouse.entity.Product;
import com.example.WebWarehouse.entity.User;
import com.example.WebWarehouse.model.CharOrder;
import com.example.WebWarehouse.model.OrderFormModel;
import com.example.WebWarehouse.model.ProductABC;
import com.example.WebWarehouse.repository.OrderRepository;
import com.example.WebWarehouse.repository.ProductRepository;
import com.example.WebWarehouse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final CellProductService cellProductService;
    private final ProductService productService;

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
    }

    public CharOrder getChart(User user) {
        List<Order> orders= orderRepository.findByBuyer_Id(user.getId());
        CharOrder charOrder= new CharOrder();
        for (Order order : orders){
            charOrder.getProducts().add(productRepository.findProductById(order.getProduct().getId()));
            charOrder.getQuantity().add(order.getQuantity());
            charOrder.getLocalDateTime().add(order.getOrderDate());
        }
        charOrder.setUser(user);
        return charOrder;
    }
    public List<Order> barChar(User user){
        return orderRepository.findByBuyer_Id(user.getId());
    }
    public List<Order> findBySupplier(User user){
        return orderRepository.findBySupplier_Id(user.getId());
    }
    public double calculateCost(OrderFormModel orderFormModel){
        double cost=0;
        for (int i=0;i< orderFormModel.getProductIds().size();i++){
            cost += productRepository.findProductById(orderFormModel.getProductIds().get(i)).getCost() * orderFormModel.getQuantities().get(i);
        }
        if (cost > 300) {
            double discount = cost * 0.15; // Calculate the discount amount (10% of the cost)
            cost -= discount; // Apply the discount to the cost
        }
        return cost;
    }

    public void deletByProductId(Long productId) {
        orderRepository.deleteByProductId(productId);
    }

    public  List<ProductABC> performABCAnalysis(User user){
        List<Order> orders= orderRepository.findBySupplier_Id(user.getId());
        Map<String, Double> productCostMap = new HashMap<>();

        // Вычисляем суммарную стоимость товаров с одинаковым именем
        for (Order order : orders) {
            String name = order.getProduct().getName();
            double cost = order.getCost();

            if (productCostMap.containsKey(name)) {
                // Товар уже существует, добавляем стоимость
                double totalCost = productCostMap.get(name) + cost;
                productCostMap.put(name, totalCost);
            } else {
                // Товар встречается впервые, добавляем его в карту
                productCostMap.put(name, cost);
            }
        }
        // Создаем список товаров из карты
        List<ProductABC> mergedProducts = new ArrayList<>();
        for (Map.Entry<String, Double> entry : productCostMap.entrySet()) {
            String name = entry.getKey();
            double cost = entry.getValue();
            mergedProducts.add(new ProductABC(name, cost,"0"));
        }

        // Сортируем товары по убыванию стоимости
        mergedProducts.sort(Comparator.comparingDouble(ProductABC::getRevenue).reversed());

        // Вычисляем сумму стоимости всех товаров
        double totalCost = 0;
        for (ProductABC product : mergedProducts) {
            totalCost += product.getRevenue();
        }

        // Вычисляем накопленную долю стоимости и присваиваем категории
        double accumulatedCost = 0;
        for (ProductABC product : mergedProducts) {
            double costPercentage = product.getRevenue() / totalCost;
            accumulatedCost += costPercentage;

            if (accumulatedCost <= 0.7) {
                product.setStatus("Категория А");
            } else if (accumulatedCost <= 0.9) {
                product.setStatus("Категория B");
            } else {
                product.setStatus("Категория C");
            }
        }
        return mergedProducts;
    }
}



