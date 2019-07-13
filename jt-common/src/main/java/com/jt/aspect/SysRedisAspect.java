package com.jt.aspect;


import com.jt.anno.Cache_Find;
import com.jt.enu.KEY_ENUM;
import com.jt.util.ObjectMapperUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisCluster;

/**
 * redis切面
 */
@Component
@Aspect   //标识切面
public class SysRedisAspect {

    @Autowired
    private JedisCluster jedis;

    @Around("@annotation(cache_find)")
    public Object around(ProceedingJoinPoint joinPoint, Cache_Find cache_find){
        //获取key的值
        String key = getKey(joinPoint,cache_find);
        //根据key查询缓存
        String result = jedis.get(key);
        Object data = null;
        try {
            if (StringUtils.isEmpty(result)){
                //如果结果为null,则表示缓存中没有数据
                //查询数据库
                //data = joinPoint.proceed();   //表示业务方法执行
                data = joinPoint.proceed();
                //将数据转换为json字符串
                String json = ObjectMapperUtil.toJSON(data);
                //判断用户是否设定超时时间
                if(cache_find.secondes() == 0){
                    //表示不要超时
                    jedis.set(key,json);
                }else{
                    jedis.setex(key,cache_find.secondes(),json);
                }
                System.out.println("查询数据库!!!!");

            }else {
                Class tagetClass = getClass(joinPoint);
                //如果缓存中有数据,则将数据转换为对象
                data = ObjectMapperUtil.toObject(result,tagetClass);
                System.out.println("aop查询缓存!");
            }
        }catch (Throwable t){
            t.printStackTrace();
            throw new RuntimeException(t);
        }
        return data;
    }



    private String getKey(ProceedingJoinPoint joinPoint,Cache_Find cache_find){
        //1.获取key的类型
        KEY_ENUM keyEnum = cache_find.keyType();
        //判断key的类型
        if(keyEnum.equals(KEY_ENUM.EMPTY)){
            //表示使用的是自己的key
            return cache_find.key();
        }
        //表示用户的key需要拼接  key+"_" + 第一个参数
        String strArgs = String.valueOf(joinPoint.getArgs()[0]);
        String key = cache_find.key() + "_" + strArgs;
        return key;
    }

    /**
     * 获取返回值类型
     * @param joinPoint
     * @return
     */
    private Class getClass(ProceedingJoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getReturnType();
    }

}
