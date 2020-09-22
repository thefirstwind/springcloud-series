package com.thefirstwind.catalogservice.web.controllers;

import com.thefirstwind.catalogservice.entities.Product;
import com.thefirstwind.catalogservice.exceptions.ProductNotFoundException;
import com.thefirstwind.catalogservice.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public List<Product> allProducts(){
        return productService.findAllProducts();
    }

    public Product productByCode(@PathVariable String code){
        return productService.findProductByCode(code)
                .orElseThrow(() -> new ProductNotFoundException("Product with code ["+code+"] doesn't exist"));
    }


}