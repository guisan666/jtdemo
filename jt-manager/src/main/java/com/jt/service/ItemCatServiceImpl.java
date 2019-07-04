package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.vo.EasyUI_Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private ItemCatMapper itemCatMapper;

    @Override
    public String findItemCatNameById(Long itemCatId) {
        ItemCat itemCat = itemCatMapper.selectById(itemCatId);
        return itemCat.getName();
    }

    @Override
    public List<EasyUI_Tree> findTree(Long parentId) {
        //获取数据库的数据
        List<ItemCat> itemCatList = findItemCats(parentId);

        List<EasyUI_Tree> trees = new ArrayList<>();
        for (ItemCat itemCat: itemCatList){
            EasyUI_Tree tree = new EasyUI_Tree();
            tree.setId(itemCat.getId());
            tree.setText(itemCat.getName());
            String state = itemCat.getIsParent() ? "closed" : "open";
            tree.setState(state);
            trees.add(tree);
        }

        return trees;
    }

    public List<ItemCat> findItemCats(Long parentId){
        QueryWrapper<ItemCat> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",parentId);
        return itemCatMapper.selectList(wrapper);
    }
}
