package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUI_Image;
import com.jt.vo.EasyUI_Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private ItemCatMapper itemCatMapper;
    @Autowired
    private Jedis jedis;

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

    @Override
    public List<EasyUI_Tree> findItemCatByCache(Long parentId) {
        List<EasyUI_Tree> treeList = new ArrayList<>();
        String key = "ITEM_CAT_" + parentId;
        String result = jedis.get(key);

        if (StringUtils.isEmpty(result)){  //如果为空
            treeList = findTree(parentId);
            //将对象转化为json
            String json = ObjectMapperUtil.toJSON(treeList);
            jedis.set(key,json);
        }else{  //缓存中有数据
             treeList = ObjectMapperUtil.toObject(result, List.class);
        }
        return treeList;
    }
}
