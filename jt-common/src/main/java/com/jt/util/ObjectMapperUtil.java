package com.jt.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 */
public class ObjectMapperUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将对象转化为json
     */
    public static String toJSON(Object obj){
        String json = null;
        try {
            json = MAPPER.writeValueAsString(obj);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return json;
    }

    public static <T> T toObject(String json,Class<T> targetClass){
        T obj = null;
        try {
            obj = MAPPER.readValue(json,targetClass);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return obj;
    }

}
