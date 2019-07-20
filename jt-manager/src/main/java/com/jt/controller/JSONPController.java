package com.jt.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.ItemCat;
import com.jt.service.ItemCatService;
import com.jt.util.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JSONPController {

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/web/testJSONP")
    public JSONPObject testJsonp(String callback){
        ItemCat itemCat = new ItemCat();
        itemCat.setId(1000L);
        itemCat.setName("张三丰");
        String json = ObjectMapperUtil.toJSON(itemCat);
        JSONPObject object = new JSONPObject(callback,itemCat);
        return object;
    }
}
