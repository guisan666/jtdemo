package com.jt.controller;

import com.jt.anno.Cache_Find;
import com.jt.enu.KEY_ENUM;
import com.jt.mapper.ItemCatMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemCat;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemDescService;
import com.jt.vo.EasyUI_Table;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.service.ItemService;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemDescService itemDescService;
	@Autowired
	private ItemCatMapper itemCatMapper;
	@Autowired
	private ItemMapper itemMapper;

	/**
	 * 根据分页查询实现查询
	 */
	@RequestMapping("/query")
	public EasyUI_Table findItemByPages(Integer page ,Integer rows){
		return itemService.findItemByPages(page,rows);
	}

	@RequestMapping("/save")
	public SysResult saveItem(Item item, ItemDesc itemDesc){
			itemService.saveObject(item,itemDesc);
			return SysResult.success();
	}

	@RequestMapping("/update")
	public SysResult updateItem(Item item,ItemDesc itemDesc){
		itemService.updateItem(item,itemDesc);
		return SysResult.success();
	}

	@RequestMapping("/delete")
	public SysResult deleteItems(Long[] ids){
		itemService.deleteItems(ids);
		return SysResult.success();
	}

	/**
	 * 商品下架
	 * @return
	 */
	@RequestMapping("/instock")
	public SysResult itemInstock(Long[] ids){
		int status = 2;
		itemService.updateStatus(ids,status);
		return SysResult.success();
	}

	/**
	 * 商品上架
	 * @return
	 */
	@RequestMapping("/reshelf")
	public SysResult itemReshelf(Long[] ids){
		int status = 1;
		itemService.updateStatus(ids,status);
		return SysResult.success();
	}

    /**
     * 商品查询描述回显
     */

    @RequestMapping("/query/item/desc/{id}")
    public SysResult queryDesc(@PathVariable Long id){
        ItemDesc itemDesc = itemDescService.queryById(id);
        return SysResult.success(itemDesc);
    }

//    @RequestMapping("/param/item/query/{id}")
//    public SysResult queryParam(@PathVariable Long id){
//		//查询当前id 的cid
//		Item item = itemMapper.selectById(id);
//		Long cid = item.getCid();
//		ItemCat itemCat = itemCatMapper.selectById(cid);
//		System.out.println("++++++++++" + itemCat);
//		return SysResult.success(itemCat.getName());
//	}


}
