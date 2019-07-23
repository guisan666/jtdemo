package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.Order;
import com.jt.service.DubboCartService;
import com.jt.service.DubboOrderService;
import com.jt.threadLocal.UserThreadLocal;
import com.jt.vo.SysResult;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Reference(timeout = 3000,check = false)
    private DubboCartService dubboCartService;

    @Reference
    private DubboOrderService dubboOrderService;

    @RequestMapping("/create")
    public String create(Model model){
        //获取用户的购物车
        Long userId = UserThreadLocal.get().getId();
        List<Cart> cartList  = dubboCartService.findCartListByUserId(userId);
        model.addAttribute("carts",cartList);
        return "order-cart";
    }

    @RequestMapping("/submit")
    @ResponseBody
    public SysResult saveOrder(Order order){
        Long userId = UserThreadLocal.get().getId();
        order.setUserId(userId);
        String orderId = dubboOrderService.save(order);
        //校验orderId是否有值
        if(StringUtils.isEmpty(orderId)){
            return SysResult.fail();
        }
        return SysResult.success(orderId);
    }

    @RequestMapping("/success")
    public String findOrderById(String id , Model model){
        Order order = dubboOrderService.findOrderById(id);
        model.addAttribute("order",order);
        return "success";
    }


}
