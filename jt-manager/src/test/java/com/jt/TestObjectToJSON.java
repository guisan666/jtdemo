package com.jt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.ItemDesc;
import org.junit.Test;

import java.util.Date;

public class TestObjectToJSON {

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void toJson(){
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(1000L).setItemDesc("hehe").setCreated(new Date()).setUpdated(new Date());
        try {
            String json = mapper.writeValueAsString(itemDesc);
            System.out.println(json);

            ItemDesc desc = mapper.readValue(json,ItemDesc.class);
            System.out.println(desc);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
