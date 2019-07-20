package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.service.DubboCartService;
import com.jt.threadLocal.UserThreadLocal;
import com.jt.vo.SysResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Reference(timeout = 3000,check = false)
    private DubboCartService dubboCartService;

    @RequestMapping("/show")
    public String show(Model model, HttpServletRequest request){
        //User user =(User) request.getAttribute("JT_USER");
        Long userId = UserThreadLocal.get().getId();
        List<Cart> carts = dubboCartService.findCartListByUserId(userId);
        model.addAttribute("cartList",carts);
        return "cart";
    }

    @RequestMapping("/delete/{itemId}")
    public String deleteCart(Cart cart){
        Long userId = UserThreadLocal.get().getId();
        cart.setUserId(userId);
        dubboCartService.deleteCart(cart);
        return "redirect:/cart/show.html";
    }

    @RequestMapping("/add/{itemId}")
    public String addCart(Cart cart){
        Long userId = UserThreadLocal.get().getId();
        cart.setUserId(userId);
        dubboCartService.addCart(cart);

        return "redirect:/cart/show.html";
    }

    @RequestMapping("/update/num/{itemId}/{num}")
    @ResponseBody
    public SysResult updateCartNum(Cart cart){
        dubboCartService.updateCartNum(cart);
        return SysResult.success();
    }

}
