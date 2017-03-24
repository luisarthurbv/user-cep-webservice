package com.luisarthurbv.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtils {

    public static ResponseEntity<Map> invalidArgumentsResponse(String error) {
        Map response = new HashMap();
        response.put("error", error);
        return new ResponseEntity<Map>(response, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<Map> notFoundResponse(String error) {
        Map response = new HashMap();
        response.put("error", error);
        return new ResponseEntity<Map>(response, HttpStatus.NOT_FOUND);
    }

}
