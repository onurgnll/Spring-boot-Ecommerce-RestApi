package com.ecommerce.app.service;

import com.ecommerce.app.Exceptions.AlreadyExistException;
import com.ecommerce.app.Exceptions.NotFoundException;
import com.ecommerce.app.entity.Category;
import com.ecommerce.app.entity.Product;
import com.ecommerce.app.repos.CategoryRepo;
import com.ecommerce.app.requests.CreateCategoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepo categoryRepo;

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }


    public List<Category> getCategories(){
        return categoryRepo.findAll();
    }

    public Category save(Category category){
        return categoryRepo.save(category);
    }


    public Category findById(Long id){
        return categoryRepo.findById(id).orElseThrow(()-> new NotFoundException("Category not found given id"));
    }

    public Category createCategory(CreateCategoryRequest createCategoryRequest){

        if(categoryRepo.findCategoryByName(createCategoryRequest.getName()) != null){
            throw  new AlreadyExistException("The category already exist which given name");
        }

        Category category = new Category();
        category.setName(createCategoryRequest.getName());

        return save(category);

    }


    public String addProductToCategory(String categoryName,Long productId){
        Product product = productService.findById(productId);

        Category category = categoryRepo.findCategoryByName(categoryName);

        if(product.getCategories().contains(category)){
            throw new AlreadyExistException("This product already exist the category given id");
        }

        category.getProducts().add(product);
        category.setItemCount(category.getItemCount() + 1);

        save(category);

        return "Successfully added";

    }


}
