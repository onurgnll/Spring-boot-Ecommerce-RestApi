package com.ecommerce.app.requests;

import lombok.Data;

@Data
public class RemoveFromCartList {


    private Long productId;

    private Long quantity;
}
