package com.ecommerce.app.requests;

import lombok.Data;

@Data
public class CreateCommentRequest {

    private Long userId;
    private String content;

}
