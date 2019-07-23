package com.jt.intercepter;

import com.jt.pojo.User;
import com.jt.threadLocal.UserThreadLocal;
import com.jt.util.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserIntercepter implements HandlerInterceptor {

    @Autowired
    private JedisCluster jedisCluster;

    /**
     * true表示放行  false表示拦截,一般配合重定向
     *
     * 业务实现步骤
     *  1.获取cookie中的token信息
     *  2.校验数据是否有效
     *  3.校验redis中是否有数据
     *
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取cookie中的token
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies.length > 0 ){
            for (Cookie cookie : cookies){
                if ("JT_TICKET".equals(cookie.getName())){
                    token = cookie.getValue();
                    break;
                }
            }
        }
        //校验token的有效性
        if (!StringUtils.isEmpty(token)){
            String userJson = jedisCluster.get(token);
            if (!StringUtils.isEmpty(userJson)){
                User user = ObjectMapperUtil.toObject(userJson,User.class);
                //将user数据通过request对象封装
                request.setAttribute("JT_USER",user);
                //利用threadLocal实现数据共享
                UserThreadLocal.set(user);
                return true;
            }
        }
        response.sendRedirect("/user/login.html");
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.remove();
    }


}
