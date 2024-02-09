package com.ecommerce.app.requests;

import lombok.Data;

@Data
public class ProductUpdateRequest {

    private String name;
    private Double price;
    private String description;
}
