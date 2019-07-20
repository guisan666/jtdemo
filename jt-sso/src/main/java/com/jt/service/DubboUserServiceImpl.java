package com.jt.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import redis.clients.jedis.JedisCluster;

import java.util.Date;

@Service(timeout = 3000)
public class DubboUserServiceImpl implements DubboUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public void saveUser(User user) {
        String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());  //加密后的密码
        user.setEmail(user.getPhone())
                .setPassword(md5Password)
                .setCreated(new Date())
                .setUpdated(user.getCreated());   //暂时使用电话号码代替邮箱
        userMapper.insert(user);
    }

    /**
     * 用户信息校验
     *      密码加密处理
     * 查询数据库
     *
     * @param user
     * @return token
     */
    @Override
    public String doLogin(User user) {
        String md5Pwd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Pwd);
        QueryWrapper<User> wrapper = new QueryWrapper<>(user);
        User userDB = userMapper.selectOne(wrapper);
        String token = null;
        if (userDB != null){
            //将用户数据保存到redis中 ,生存key
            String tokenTemp = "JT_TICKET_" + System.currentTimeMillis() + user.getPassword();
            tokenTemp = DigestUtils.md5DigestAsHex(tokenTemp.getBytes());
            //生存value数据
            //为了安全,需要将数据进行脱敏处理
            userDB.setPassword("******");
            String userJson = ObjectMapperUtil.toJSON(userDB);
            jedisCluster.setex(tokenTemp,7*24*3600,userJson);
            System.out.println(jedisCluster.get(tokenTemp));
            token = tokenTemp;
        }
        return token;
    }
}
