package com.jt.service;

import com.jt.anno.Cache_Find;
import com.jt.enu.KEY_ENUM;
import com.jt.mapper.ItemDescMapper;
import com.jt.pojo.ItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemDescServiceImpl implements ItemDescService {

    @Autowired
    private ItemDescMapper itemDescMapper;

    /** 根据商品id查询商品描述*/
    @Cache_Find(key = "ITEM",keyType = KEY_ENUM.AUTO)
    @Override
    public ItemDesc queryById(Long id) {
        return itemDescMapper.selectById(id);
    }
}
