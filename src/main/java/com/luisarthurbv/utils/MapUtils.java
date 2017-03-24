package com.luisarthurbv.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class MapUtils {

    public static Map convertObject(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(obj, Map.class);
    }

}
