package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.util.HttpClientService;
import com.jt.util.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private HttpClientService httpClient;

    @Override
    public Item findItemById(Long itemId) {
        String url = "http://manager.jt.com/web/item/findItemById/" + itemId;
        //System.out.println("查询商品的url" + url);
        String itemJson = httpClient.doGet(url);
        return ObjectMapperUtil.toObject(itemJson,Item.class);
    }

    @Override
    public ItemDesc findItemDescById(Long itemId) {

        String url = "http://manager.jt.com/web/item/findItemDescById/" + itemId;
       // System.out.println("查询商品详情的URL" + url);
        String itemDescJson = httpClient.doGet(url);
       // System.out.println("结果" + itemDescJson);
        return ObjectMapperUtil.toObject(itemDescJson,ItemDesc.class);
    }
}
