package com.thefirstwind.catalogservice.web.controllers;

import com.thefirstwind.catalogservice.entities.Product;
import com.thefirstwind.catalogservice.exceptions.ProductNotFoundException;
import com.thefirstwind.catalogservice.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RefreshScope
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @Value("${product.limit}")
    private Integer productLimit;

    @GetMapping("")
    public List<Product> allProducts(){
        return productService.findAllProducts();
    }

    @GetMapping("/{code}")
    public Product productByCode(@PathVariable String code){
        return productService.findProductByCode(code)
                .orElseThrow(() -> new ProductNotFoundException("Product with code ["+code+"] doesn't exist"));
    }

    @GetMapping("/getProductLimit")
    public Integer getProductLimit(){
        return productLimit;
    }

}
