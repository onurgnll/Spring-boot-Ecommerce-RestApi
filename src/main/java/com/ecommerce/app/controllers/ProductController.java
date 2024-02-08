package com.ecommerce.app.controllers;

import com.ecommerce.app.requests.ProductCreateRequest;
import com.ecommerce.app.requests.ProductUpdateRequest;
import com.ecommerce.app.responses.ResponseHandler;
import com.ecommerce.app.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllProducts(){
        return ResponseHandler.generateResponse(200, productService.findAll());
    }

    @PostMapping
    public ResponseEntity<Object> createProducts(@RequestBody ProductCreateRequest productCreateRequest){
        return ResponseHandler.generateResponse(200, productService.createProduct(productCreateRequest));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable Long id){
        return ResponseHandler.generateResponse(200, productService.findById(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable Long id){
        productService.deleteById(id);
        return ResponseHandler.generateResponse(200);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id, @RequestBody ProductUpdateRequest productUpdateRequest){

        return ResponseHandler.generateResponse(200, productService.updateProduct(productUpdateRequest,id));
    }
}
