package com.thefirstwind.catalogservice.services;

import com.thefirstwind.catalogservice.entities.Product;
import com.thefirstwind.catalogservice.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class ProductService {

//    private final ProductRepository productRepository;

    @Autowired
    private ProductRepository productRepository;

//    @Autowired
//    public ProductService(ProductRepository productRepository){
//        this.productRepository = productRepository;
//    }

    public List<Product> findAllProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> findProductByCode(String code){
        return productRepository.findByCode(code);
    }
}
