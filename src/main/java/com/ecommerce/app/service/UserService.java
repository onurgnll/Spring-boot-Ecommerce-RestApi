package com.ecommerce.app.service;

import com.ecommerce.app.Exceptions.AlreadyExistException;
import com.ecommerce.app.Exceptions.NotFoundException;
import com.ecommerce.app.entity.CartItem;
import com.ecommerce.app.entity.CartItemId;
import com.ecommerce.app.entity.Product;
import com.ecommerce.app.entity.User;
import com.ecommerce.app.repos.UserRepo;
import com.ecommerce.app.requests.AddToCartRequest;
import com.ecommerce.app.requests.AddToFavoritesRequest;
import com.ecommerce.app.requests.RemoveFromCartList;
import com.ecommerce.app.requests.UserCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserService {

    private UserRepo userRepo;
    private ProductService productService;
    private CartItemService cartItemService;

    @Autowired
    public void setCartItemService(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public User findById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new NotFoundException("User not found was given id: " + id.toString()));
    }

    public User save(User user) {
        return userRepo.save(user);
    }

    protected boolean isValidEmail(String email) {
        String regexPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return Pattern.matches(regexPattern, email);

    }

    public User createUser(UserCreateRequest userCreateRequest) {
        if (userRepo.findUserByEmail(userCreateRequest.getEmail()) != null)
            throw new AlreadyExistException("this email already used");

        if (!isValidEmail(userCreateRequest.getEmail()))
            throw new RuntimeException("This email is not valid");


        User user = new User();
        user.setName(userCreateRequest.getName());
        user.setEmail(userCreateRequest.getEmail());
        user.setPassword(userCreateRequest.getPassword());

        User savedUser = save(user);
        return savedUser;
    }


    public List<Product> findProductsById(Long id) {
        findById(id);
        return productService.findProductsByUserId(id);
    }


    public List<Product> getUserFavorites(Long id) {
        User user = findById(id);

        return user.getFavorites();
    }

    public void addToFavorites(AddToFavoritesRequest addToFavoritesRequest, Long userId) {

        User user = findById(userId);

        Product product = productService.findById(addToFavoritesRequest.getProductId());

        if (user.getFavorites().contains(product))
            throw new AlreadyExistException("This product already contains favorite list");
        user.getFavorites().add(product);

        save(user);

    }

    public void deleteFromFavorites(AddToFavoritesRequest addToFavoritesRequest, Long id) {

        User user = findById(id);

        Product product = productService.findById(addToFavoritesRequest.getProductId());

        if (!user.getFavorites().contains(product)) {
            throw new NotFoundException("This product not in user's favorite list");
        }

        user.getFavorites().remove(product);
        save(user);


    }


    public CartItem addToCart(AddToCartRequest addToCartRequest, Long userId) {

        User user = findById(userId);
        Product product = productService.findById(addToCartRequest.getProductId());

        if (product == null || user == null) {
            return null;
        }

        CartItemId cartItemId = new CartItemId();
        cartItemId.setUserId(userId);
        cartItemId.setProductId(product.getProductId());

        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(cartItemId);
        cartItem.setProduct(product);
        cartItem.setUser(user);
        cartItem.setQuantity(addToCartRequest.getQuantity());

        if (user.getCartItems().isEmpty()) {

            return cartItemService.save(cartItem);
        }
        List<CartItem> usersCart = user.getCartItems();

        for (CartItem c : usersCart) {
            if (c.getCartItemId().equals(cartItemId)) {
                c.setQuantity(c.getQuantity() + addToCartRequest.getQuantity());
                save(user);
                return c;
            }
        }


        return cartItemService.save(cartItem);
    }

    public List<CartItem> findAllCartItemsById(Long id) {
        User user = findById(id);

        return user.getCartItems();
    }

    public void removeFromCartList(RemoveFromCartList removeFromCartList, Long id) {
        User user = findById(id);

        List<CartItem> cartItems = user.getCartItems();

        Long productId = removeFromCartList.getProductId();

        if (cartItems.isEmpty()) {
            throw new NotFoundException("Bu ürün sepette yok");
        }

        for (CartItem cartItem : cartItems) {

            if (cartItem.getProduct().getProductId().equals(productId)) {
                if (cartItem.getQuantity() > 1) {
                    cartItem.setQuantity(cartItem.getQuantity() - removeFromCartList.getQuantity().intValue());

                    if (cartItem.getQuantity() <= 1) {

                        cartItemService.deleteById(cartItem.getCartItemId());
                        return;
                    }

                    break;

                }
            }
        }
        save(user);


    }


    public void clearCart(Long id) {
        cartItemService.deleteAllByUserId(id);
    }

}
