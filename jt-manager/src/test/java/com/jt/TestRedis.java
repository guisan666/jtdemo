package com.jt;

import com.jt.mapper.ItemDescMapper;
import com.jt.pojo.ItemDesc;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * 测试Redis
 */
public class TestRedis {

    @Test
    public void  testRedis1(){
        String host = "192.168.21.130";
        int port = 6379;
        Jedis jedis = new Jedis(host,port);
        jedis.set("1903","hello world");
        System.out.println(jedis.get("1903"));
        //设定数据的超时时间
        jedis.expire("1903",20);
        try {
            Thread.sleep(2000);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("还剩" + jedis.ttl("1903"));
    }

    /**
     * 简化超时
     */
    @Test
    public void testRedis2(){
        Jedis jedis = new Jedis("192.168.21.130",6379);
        jedis.setex("video",50,"复仇者联盟");
        System.out.println(jedis.get("video"));
    }

    /**
     * 锁机制用法
     */
    @Test
    public void testRedis3(){
        Jedis jedis = new Jedis("192.168.21.130",6379);
        //jedis.set("jianmian","8点锦江宾馆");
        //jedis.set("jianmian","5点锦江宾馆");

        Long flag1 = jedis.setnx("jianmian","8点锦江宾馆");
        Long flag2 = jedis.setnx("jianmian","5点锦江宾馆");
        System.out.println(jedis.get("jianmian"));
        System.out.println(flag1 + "::::" + flag2);
    }

    /**
     * 锁机制优化
     */
    @Test
    public void testRedis4(){
        Jedis jedis = new Jedis("192.168.21.130",6379);
        jedis.set("jianmian","今晚八点","NX","EX",40);
        jedis.set("jianmian","今晚无点","NX","EX",40);
    }

    @Test
    public void testRedisHash1(){
        Jedis jedis = new Jedis("192.168.21.130",6379);
        jedis.hset("user","id","1001");
        jedis.hset("user","name","tomcat");
        jedis.hset("user","age","20");

        System.out.println(jedis.hget("user","name"));
        System.out.println(jedis.hgetAll("user"));
    }

    @Test
    public void testRedisList(){
        Jedis jedis = new Jedis("192.168.21.130",6379);
        //1.当队列
        jedis.lpush("list","1","2","3","4","5");
        System.out.println(jedis.rpop("list"));
    }


    @Test
    public void testTx(){
        Jedis jedis = new Jedis("192.168.21.130",6379);
        Transaction multi = jedis.multi();
        try {
            multi.set("name", "wangciwang");
            multi.set("type","bianzhongren");
        }catch (Exception e){
            e.printStackTrace();
            multi.discard();  //回滚
        }
        multi.exec();  //提交
    }

    @Autowired
    private Jedis jedis;

    @Autowired
    private ItemDescMapper mapper;

//    public void testRedisItemDesc(){
//        String key = "10";
//        ItemDesc desc = mapper.selectById(key);
//
//    }



}
