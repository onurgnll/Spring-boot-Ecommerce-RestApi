package com.ecommerce.app.service;

import com.ecommerce.app.Exceptions.NotFoundException;
import com.ecommerce.app.entity.Product;
import com.ecommerce.app.entity.User;
import com.ecommerce.app.repos.ProductRepo;
import com.ecommerce.app.requests.ProductCreateRequest;
import com.ecommerce.app.requests.ProductUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.sound.sampled.Port;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class ProductService {

    private ProductRepo productRepo;
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public Page<Product> findAll(int page){
        Pageable pageable = PageRequest.of(page, 10);
        return productRepo.findAll(pageable);
    }


    public Product findById(Long id){
        return productRepo.findById(id).orElseThrow(()-> new NotFoundException("The product not found was given id: "+id.toString() ));
    }


    public Product save(Product product){
        return productRepo.save(product);
    }


    public void deleteById(Long id){
        findById(id);
        productRepo.deleteById(id);
    }

    public Product updateProduct(ProductUpdateRequest productUpdateRequest , Long id){

        Product product = findById(id);

        product.setDescription(productUpdateRequest.getDescription());
        product.setName(productUpdateRequest.getName());
        product.setPrice(productUpdateRequest.getPrice());

        return save(product);

    }


    public Product createProduct(ProductCreateRequest productCreateRequest){

        Product product = new Product();

        product.setCreatedAt(LocalDateTime.now());
        product.setDescription(productCreateRequest.getDescription());
        product.setName(productCreateRequest.getName());
        product.setPrice(productCreateRequest.getPrice());

        User user = userService.findById(productCreateRequest.getUserId());

        product.setUser(user);

        return save(product);
    }


    public Page<Product> findProductsByUserId(Long userId, int page){
        User user = userService.findById(userId);

        Pageable pageable = PageRequest.of(page, 10);

        return productRepo.findProductsByUserUserId(userId,pageable);
    }

}
