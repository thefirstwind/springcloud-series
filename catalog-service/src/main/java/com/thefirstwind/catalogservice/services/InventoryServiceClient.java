package com.thefirstwind.catalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.thefirstwind.catalogservice.web.models.ProductInventoryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Slf4j
public class InventoryServiceClient {

    @Autowired
    private RestTemplate restTemplate;


    @HystrixCommand(
            commandKey = "inventory-by-productcode"
            ,fallbackMethod = "getDefaultProductInventoryByCode"
//            ,commandProperties = {
//                @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="3000"),
//                @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="60")
//            }
    )
    public Optional<ProductInventoryResponse> getProductInventoryByCode(String productCode){
        ResponseEntity<ProductInventoryResponse> itemResponseEntity =
                restTemplate.getForEntity("http://inventory-service/api/inventory/{code}",
                ProductInventoryResponse.class,
                productCode);
        if(itemResponseEntity.getStatusCode() == HttpStatus.OK){
            return Optional.ofNullable(itemResponseEntity.getBody());
        }else{
            log.error("Unable to get inventory level for product_code: " + productCode + ", StatusCode: " + itemResponseEntity.getStatusCode());
            return Optional.empty();
        }
    }

    public Optional<ProductInventoryResponse> getDefaultProductInventoryByCode(String productCode){
        log.info("Returning default ProductInventoryByCode for productCode: "+productCode);
        ProductInventoryResponse response = new ProductInventoryResponse();
        response.setProductCode(productCode);
        response.setAvailableQuantity(50);
        return Optional.ofNullable(response);
    }

}
