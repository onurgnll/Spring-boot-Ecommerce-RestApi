package com.ecommerce.app.service;

import com.ecommerce.app.Exceptions.NotFoundException;
import com.ecommerce.app.entity.*;
import com.ecommerce.app.repos.CommentRepo;
import com.ecommerce.app.requests.CreateCommentRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class CommentService {

    private CommentRepo commentRepo;
    private UserService userService;
    private ProductService productService;



    public CommentService(CommentRepo commentRepo, UserService userService, ProductService productService) {
        this.commentRepo = commentRepo;
        this.userService = userService;
        this.productService = productService;
    }


    private Comment save(Comment comment){
        return commentRepo.save(comment);
    }


    public List<Comment> findCommentsByUserId(Long id){

        User user = userService.findById(id);
        return user.getComments();
    }

    public List<Comment> findCommentsByProductId(Long id){

        Product product = productService.findById(id);
        return product.getComments();
    }

    public Comment createComment(CreateCommentRequest createCommentRequest, Long productId){

        Product product = productService.findById(productId);
        User user = userService.findById(createCommentRequest.getUserId());

        boolean isUserBought = false;

        for (Order order : user.getOrders()) {
            boolean isFound = false;
            for(OrderItem orderItem : order.getOrderItems()){
                if (Objects.equals(orderItem.getProduct().getProductId(), product.getProductId())){

                    isUserBought = true;
                    isFound= true;
                    break;
                }
            }
            if(isFound){
                break;
            }
        }

        if(isUserBought){

            CommentId commentId = new CommentId();
            commentId.setProductId(product.getProductId());
            commentId.setUserId(user.getUserId());

            Comment comment = new Comment();
            comment.setCommentId(commentId);

            comment.setContent(createCommentRequest.getContent());
            comment.setCreatedAt(LocalDateTime.now());
            comment.setUser(user);
            comment.setProduct(product);

            return save(comment);
        }else {
            throw new NotFoundException("Ürünü satın almamışsınız");
        }




    }

}
