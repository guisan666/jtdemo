package com.jt.controller;


import com.jt.service.ItemCatService;
import com.jt.vo.EasyUI_Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item/cat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/queryItemName")
    public String queryItemName(Long itemCatId){
        return itemCatService.findItemCatNameById(itemCatId);
    }

    @RequestMapping("/list")
    public List<EasyUI_Tree> findItemCatByParentId(@RequestParam(name="id" ,defaultValue = "0") Long parentId){
        return itemCatService.findTree(parentId);
    }

}
