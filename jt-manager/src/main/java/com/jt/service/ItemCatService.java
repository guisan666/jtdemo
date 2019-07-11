package com.jt.service;

import com.jt.pojo.ItemCat;
import com.jt.vo.EasyUI_Tree;

import java.util.List;

public interface ItemCatService {

    String findItemCatNameById(Long itemCatId);

    List<EasyUI_Tree> findTree(Long parentId);

    List<EasyUI_Tree> findItemCatByCache(Long parentId);

}
