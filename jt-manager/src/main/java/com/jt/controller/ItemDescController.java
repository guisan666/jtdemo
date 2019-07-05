package com.jt.controller;

import com.jt.service.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item")
public class ItemDescController {

    @Autowired
    private ItemDescService itemDescService;

}
