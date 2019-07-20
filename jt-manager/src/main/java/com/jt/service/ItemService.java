package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUI_Table;

public interface ItemService {

    EasyUI_Table findItemByPages(Integer page ,Integer rows);

    void saveObject(Item item, ItemDesc itemDesc);

    void updateItem(Item item,ItemDesc itemDesc);

    void deleteItems(Long[] ids);

    void updateStatus(Long[] ids , Integer status);

    Item findItemById(Long id);


}
