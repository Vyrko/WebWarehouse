package com.example.WebWarehouse.services;

import com.example.WebWarehouse.entity.CellProduct;
import com.example.WebWarehouse.entity.Order;
import com.example.WebWarehouse.entity.Product;
import com.example.WebWarehouse.entity.User;
import com.example.WebWarehouse.model.OrderFormModel;
import com.example.WebWarehouse.repository.ProductRepository;
import com.example.WebWarehouse.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService{
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    public boolean saveProduct(Product product, User user) throws IOException{
        product.setUser(user);
        productRepository.save(product);
        return true;
    }
    public boolean saveAllProducts(List<Product> productList, User user){
        for (Product product : productList){
            product.setUser(user);
            productRepository.save(product);
        }
        return true;
    }

    public List<Product> AllProduct() {
        return productRepository.findAll();
    }


    public List<Product> getAllProductByUserId(Long id) {
        return productRepository.findByUserId(id);
    }

    public Product findById(Long productId) {return productRepository.findProductById(productId);}
    public void deleteById(Long productId) {productRepository.deleteById(productId);}

    public void update(Product product) {
        Product productDB = productRepository.findProductById(product.getId());
        productDB.copyProduct(product);
        productRepository.save(productDB);
    }

    public void decreaseQuantity(int quantity, Long id) {
        Product productDB = findById(id);
        productDB.setQuantity(productDB.getQuantity() - quantity);
        productRepository.save(productDB);
    }
    public void updateAfterOrder(OrderFormModel orderFormModel){
        OrderFormModel OrderModelAfterVerif = verificationProduct(orderFormModel);
        increaseQuantity(OrderModelAfterVerif);
        setPostman(OrderModelAfterVerif);
    }
    public void increaseQuantity(OrderFormModel orderFormModel) {
        for (int i = 0; i< orderFormModel.getProductIds().size();i++){
            Product productDB = findById(orderFormModel.getProductIds().get(i));
            productDB.setQuantity(productDB.getQuantity() + orderFormModel.getQuantities().get(i));
            productRepository.save(productDB);
        }
    }
    public void setPostman(OrderFormModel orderFormModel){
        for (int i = 0; i< orderFormModel.getProductIds().size();i++){
            Product productDB = findById(orderFormModel.getProductIds().get(i));
            productDB.setPostman(userRepository.getById(orderFormModel.getSupplier()).getName());
            productRepository.save(productDB);
        }
    }
    public OrderFormModel verificationProduct(OrderFormModel orderFormModel){
        for (int i=0;i<orderFormModel.getProductIds().size();i++){
            Product existingProduct= productRepository.findProductById(orderFormModel.getProductIds().get(i));
            if (getByUserAndName(orderFormModel.getBuyer(),existingProduct.getName()) == null){
                Product newProduct =  new Product();
                newProduct.setUser(userRepository.getById(orderFormModel.getBuyer()));
                newProduct.setQuantity(0);
                newProduct.setCost(existingProduct.getCost());
                newProduct.setName(existingProduct.getName());
                newProduct.setPostman(userRepository.getById(orderFormModel.getSupplier()).getName());
                newProduct.setSize(existingProduct.getSize());
                productRepository.save(newProduct);
                // короче перепиши цикл на int i и допиши сюда замену несуществующего товара на новый из бд
            }
            orderFormModel.getProductIds().set(i,getByUserAndName(
                    orderFormModel.getBuyer(),
                    existingProduct.getName()
            ));
        }
        return orderFormModel;
    }
    public Long getByUserAndName(Long userId, String name){
        for (Product product : productRepository.findByUserId(userId)){
            if (product.getName().equals(name)){
                return product.getId();
            }
        }
        return null;
    }
}
