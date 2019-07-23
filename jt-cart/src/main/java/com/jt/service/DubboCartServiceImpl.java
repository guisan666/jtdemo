package com.jt.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Service(timeout = 3000)
public class DubboCartServiceImpl implements DubboCartService {

    @Autowired
    private CartMapper cartMapper;

    @Override
    public List<Cart> findCartListByUserId(Long userId) {
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        return cartMapper.selectList(wrapper);
    }

    @Override
    public void deleteCart(Cart cart) {
        QueryWrapper<Cart> wrapper = new QueryWrapper<>(cart);
        cartMapper.delete(wrapper);
    }

    /**
     * 根据user_id ,item_id 查询数据库
     * null表示第一购买
     * !null不是第一次购买,做数量的更新
     * @param cart
     */
    @Override
    public void addCart(Cart cart) {
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("item_id",cart.getItemId()).eq("user_id",cart.getUserId());
        Cart cartDB = cartMapper.selectOne(wrapper);

        if (cartDB == null ){
            cart.setCreated(new Date()).setUpdated(cart.getCreated());
            cartMapper.insert(cart);
        }else{
            int num = cartDB.getNum() + cart.getNum();
            Cart cartTemp = new Cart();
            cartTemp.setId(cartDB.getId()).setNum(num).setUpdated(new Date());
            cartMapper.updateById(cartTemp);
        }
    }

    @Override
    public void updateCartNum(Cart cart) {
        Cart cartTemp = new Cart();
        cartTemp.setNum(cart.getNum()).setUpdated(new Date());
        System.out.println(cart);
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",cart.getUserId()).eq("item_id",cart.getItemId());
        cartMapper.update(cartTemp,wrapper);
    }
}
