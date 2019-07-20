package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.generator.config.IFileCreate;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.vo.SysResult;
import com.sun.corba.se.impl.oa.toa.TOA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class UserController {

    @Reference(timeout = 3000,check = false)
    private DubboUserService dubboUserService;

    @Autowired
    private JedisCluster jedisCluster;

    @RequestMapping("/{moduleName}")
    public String moduleName(@PathVariable String moduleName){
        return moduleName;
    }

    @RequestMapping("/doRegister")
    @ResponseBody
    public SysResult saveUser(User user){
        dubboUserService.saveUser(user);
        return SysResult.success();
    }

    @RequestMapping("/doLogin")
    @ResponseBody
    public SysResult doLogin(User user, HttpServletResponse response){
        String token = dubboUserService.doLogin(user);
        //校验远程服务器返回的数据是否为空
        if (StringUtils.isEmpty(token)){
            return SysResult.fail();
        }
        //将token数据写入cookie
        Cookie cookie = new Cookie("JT_TICKET",token);
        // setMaxAge (>0)  cookie保存多少秒   (=0)删除cookie   (-1) 会话关闭cookie删除
        cookie.setMaxAge(7*24*3600);  //cookie的生存时间
        cookie.setPath("/");
        cookie.setDomain("jt.com");
        response.addCookie(cookie);
        return SysResult.success();
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request , HttpServletResponse response){
        
        //1.删除redis   (首先要有key  ,key在cookie  ---JT_TICKET)
        Cookie[] cookies = request.getCookies();
        String token = null ;
        if ( cookies.length != 0 ){
            for (Cookie cookie : cookies){
                if("JT_TICKET".equals(cookie.getName())){
                    //获取cookie的值
                    token = cookie.getValue();
                    break;
                }
            }
        }
        if (!StringUtils.isEmpty(token)){
            jedisCluster.del(token);
            Cookie cookie = new Cookie("JT_TICKET","");
            cookie.setMaxAge(0);
            cookie.setPath("/");
            cookie.setDomain("jt.com");
            response.addCookie(cookie);
        }
        return "redirect:/";
    }
}
