package com.ecommerce.app.Exceptions;

import com.ecommerce.app.responses.ResponseHandler;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundEx(NotFoundException notFoundException){

        return ResponseHandler.generateResponse(404, notFoundException.getMessage());
    }
    @ExceptionHandler
    public ResponseEntity<Object> handleAlreadyExistEx(AlreadyExistException alreadyExistException){

        return ResponseHandler.generateResponse(400, alreadyExistException.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFound(UsernameNotFoundException usernameNotFoundException){

        return ResponseHandler.generateResponse(404, usernameNotFoundException.getMessage());
    }
    @ExceptionHandler
    public ResponseEntity<Object> handleException(ExpiredJwtException expiredJwtException){
        return ResponseHandler.generateResponse(400, "this session not available");
    }
    @ExceptionHandler
    public ResponseEntity<Object> handleException(InternalAuthenticationServiceException notFoundException){
        return ResponseHandler.generateResponse(404, notFoundException.getMessage());
    }
    @ExceptionHandler
    public ResponseEntity<Object> handleException(Exception notFoundException){
        System.out.println(notFoundException.getMessage());
        System.out.println(notFoundException.getClass());
        return ResponseHandler.generateResponse(500, notFoundException.getMessage());
    }

}
