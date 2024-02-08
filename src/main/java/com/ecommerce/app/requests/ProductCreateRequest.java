package com.ecommerce.app.requests;

import lombok.Data;

@Data
public class ProductCreateRequest {

    private String name;
    private int price;
    private String description;
    private Long userId;



}
