package com.ecommerce.app.requests;

import lombok.Data;

@Data
public class AddToCategoryRequest {
    private String categoryName;
    private Long productId;

}
