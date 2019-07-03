package com.jt.controller;

import com.jt.vo.EasyUI_Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.service.ItemService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;


	/**
	 * 根据分页查询实现查询
	 */
	@RequestMapping("/query")
	public EasyUI_Table findItemByPages(Integer page ,Integer rows){
		return itemService.findItemByPages(page,rows);
	}




}
