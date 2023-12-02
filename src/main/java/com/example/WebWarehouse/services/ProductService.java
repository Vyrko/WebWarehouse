package com.example.WebWarehouse.services;

import com.example.WebWarehouse.entity.Product;
import com.example.WebWarehouse.entity.User;
import com.example.WebWarehouse.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService{
    private final ProductRepository productRepository;

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
}
