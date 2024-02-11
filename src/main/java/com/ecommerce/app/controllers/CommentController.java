package com.ecommerce.app.controllers;

import com.ecommerce.app.requests.CreateCommentRequest;
import com.ecommerce.app.responses.ResponseHandler;
import com.ecommerce.app.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Object> getProductCommentsByProductId(@PathVariable Long id , @RequestParam(name = "page", defaultValue = "0") int page){
        return ResponseHandler.generateResponse(200, commentService.findCommentsByProductId(id , page));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Object> getProductCommentsByUserId(@PathVariable Long id, @RequestParam(name = "page", defaultValue = "0") int page){
        return ResponseHandler.generateResponse(200, commentService.findCommentsByUserId(id, page));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> createComment(@RequestBody CreateCommentRequest createCommentRequest, @PathVariable Long id){
        return ResponseHandler.generateResponse(200, commentService.createComment(createCommentRequest,id));
    }


}
