package com.ecommerce.app.responses;


import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<Object> generateResponse(int httpValue , Object object , String message){

        Map<String, Object> map = new HashMap<String , Object>();

        map.put("data", object);
        map.put("status", httpValue);
        map.put("message", message);

        if(httpValue >= 400){
            map.put("success", false);
        }else {
            map.put("success", true);
        }

        return new ResponseEntity<Object>(map, HttpStatus.valueOf(httpValue));

    }
    public static ResponseEntity<Object> generateResponse(int httpValue ){

        Map<String, Object> map = new HashMap<String , Object>();

        map.put("status", httpValue);

        if(httpValue >= 400){
            map.put("success", false);
        }else {
            map.put("success", true);
        }

        return new ResponseEntity<Object>(map, HttpStatus.valueOf(httpValue));

    }
    public static ResponseEntity<Object> generateResponse(int httpValue, Object object ){

        Map<String, Object> map = new HashMap<String , Object>();

        map.put("status", httpValue);
        map.put("data", object);

        if(httpValue >= 400){
            map.put("success", false);
        }else {
            map.put("success", true);
        }

        return new ResponseEntity<Object>(map, HttpStatus.valueOf(httpValue));

    }
    public static ResponseEntity<Object> generateResponse(int httpValue , String message){

        Map<String, Object> map = new HashMap<String , Object>();

        map.put("status", httpValue);
        map.put("message", message);

        if(httpValue >= 400){
            map.put("success", false);
        }else {
            map.put("success", true);
        }

        return new ResponseEntity<Object>(map, HttpStatus.valueOf(httpValue));

    }

}
