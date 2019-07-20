package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.anno.Cache_update;
import com.jt.enu.KEY_ENUM;
import com.jt.mapper.ItemDescMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUI_Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.mapper.ItemMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;

	@Override
	public EasyUI_Table findItemByPages(Integer page, Integer rows) {

		int totle = itemMapper.selectCount(null);
		QueryWrapper<Integer> wrapper = new QueryWrapper<>();

		int start = (page-1)*rows;
		List<Item> list = itemMapper.findItemPages(start,rows);
		return new EasyUI_Table(totle,list);
	}

	@Transactional
	@Override
	public void saveObject(Item item, ItemDesc itemDesc) {
		item.setStatus(1).setCreated(new Date()).setUpdated(item.getCreated());
		itemMapper.insert(item);

		itemDesc.setItemId(item.getId()).setCreated(item.getCreated()).setUpdated(item.getUpdated());
		itemDescMapper.insert(itemDesc);
	}

	@Transactional
	@Cache_update(key = "ITEM",keyType = KEY_ENUM.AUTO)
	@Override
	public void updateItem(Item item,ItemDesc itemDesc) {
		//Item i = itemMapper.selectById(item.getId());
		item.setUpdated(new Date());
		itemDesc.setUpdated(new Date());
		itemMapper.updateById(item);
		itemDesc.setItemId(item.getId());
        itemDescMapper.updateById(itemDesc);  //修改商品描述
	}

	@Transactional
	@Override
	public void deleteItems(Long[] ids) {
		//手写sql
		//itemMapper.deleteItems(ids);
		List<Long> idList = Arrays.asList(ids);
		itemMapper.deleteBatchIds(idList);
		itemDescMapper.deleteBatchIds(idList);  //删除商品描述
	}

	@Override
	public void updateStatus(Long[] ids, Integer status) {
        //itemMapper.updateStatus(ids,status);

        Item item = new Item();


        item.setStatus(status);
        item.setUpdated(new Date());

        UpdateWrapper<Item> wrapper = new UpdateWrapper<>();
        List<Long> longList = Arrays.asList(ids);
        wrapper.in("id",longList);
        itemMapper.update(item,wrapper);
	}

	@Override
	public Item findItemById(Long id) {
		return itemMapper.selectById(id);
	}
}
