package com.ecommerce.app.repos;

import com.ecommerce.app.entity.Product;
import com.ecommerce.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepo extends JpaRepository<User , Long> {


    User findUserByEmail(String email);


}
