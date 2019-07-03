package com.jt.service;

import com.jt.vo.EasyUI_Table;

public interface ItemService {

    EasyUI_Table findItemByPages(Integer page ,Integer rows);
	
}
