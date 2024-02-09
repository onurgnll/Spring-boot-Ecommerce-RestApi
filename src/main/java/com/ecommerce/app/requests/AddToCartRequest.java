package com.ecommerce.app.requests;

import lombok.Data;

@Data
public class AddToCartRequest {

    private Long productId;
    private int quantity;
}
