package com.ecommerce.app.service;

import com.ecommerce.app.Exceptions.AlreadyExistException;
import com.ecommerce.app.Exceptions.NotFoundException;
import com.ecommerce.app.entity.Product;
import com.ecommerce.app.entity.User;
import com.ecommerce.app.repos.ProductRepo;
import com.ecommerce.app.repos.UserRepo;
import com.ecommerce.app.requests.UserCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserService {

    private UserRepo userRepo;
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> findAll(){
        return userRepo.findAll();
    }

    public User findById(Long id){
        return userRepo.findById(id).orElseThrow(() -> new NotFoundException("User not found was given id: " + id.toString()));
    }

    public User save(User user){
        return userRepo.save(user);
    }

    protected boolean isValidEmail(String email){
        String regexPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(regexPattern,email);

    }

    public User createUser(UserCreateRequest userCreateRequest) {
        if(userRepo.findUserByEmail(userCreateRequest.getEmail()) != null)
            throw new AlreadyExistException("this email already used");

        if(!isValidEmail(userCreateRequest.getEmail()))
            throw new RuntimeException("This email is not valid");

        User user = new User();
        user.setName(userCreateRequest.getName());
        user.setEmail(userCreateRequest.getEmail());
        user.setPassword(userCreateRequest.getPassword());

        return save(user);
    }


    public List<Product> findProductsById(Long id){
        findById(id);
        return productService.findProductsByUserId(id);
    }
}
