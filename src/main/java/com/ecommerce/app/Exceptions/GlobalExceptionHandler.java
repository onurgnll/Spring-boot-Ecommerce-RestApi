package com.ecommerce.app.Exceptions;

import com.ecommerce.app.responses.ResponseHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handleException(Exception notFoundException){
        System.out.println(notFoundException.getMessage());
        return ResponseHandler.generateResponse(500, notFoundException.getMessage());
    }
    @ExceptionHandler
    public ResponseEntity<Object> handleNotFoundEx(NotFoundException notFoundException){

        return ResponseHandler.generateResponse(404, notFoundException.getMessage());
    }
    @ExceptionHandler
    public ResponseEntity<Object> handleAlreadyExistEx(AlreadyExistException alreadyExistException){

        return ResponseHandler.generateResponse(400, alreadyExistException.getMessage());
    }

}
