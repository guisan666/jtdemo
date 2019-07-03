package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.pojo.Item;
import com.jt.vo.EasyUI_Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.mapper.ItemMapper;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;

	@Override
	public EasyUI_Table findItemByPages(Integer page, Integer rows) {

		int totle = itemMapper.selectCount(null);
		QueryWrapper<Integer> wrapper = new QueryWrapper<>();

		int start = (page-1)*rows;
		List<Item> list = itemMapper.findItemPages(start,rows);
		return new EasyUI_Table(totle,list);
	}
}
