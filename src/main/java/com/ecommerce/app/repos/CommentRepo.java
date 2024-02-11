package com.ecommerce.app.repos;

import com.ecommerce.app.entity.Comment;
import com.ecommerce.app.entity.CommentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, CommentId> {
}
