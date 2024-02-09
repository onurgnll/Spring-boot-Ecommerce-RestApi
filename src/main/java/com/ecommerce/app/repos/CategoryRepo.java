package com.ecommerce.app.repos;

import com.ecommerce.app.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {

    Category findCategoryByName(String name);


}
