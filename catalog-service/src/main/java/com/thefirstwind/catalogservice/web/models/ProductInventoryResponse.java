package com.thefirstwind.catalogservice.web.models;

import lombok.Data;

@Data
public class ProductInventoryResponse {

    private String productCode;

    private int availableQuantity;
}
