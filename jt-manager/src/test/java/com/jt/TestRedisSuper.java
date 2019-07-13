package com.jt;

import org.junit.Test;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 测试分片 集群 哨兵
 */
public class TestRedisSuper {

    /**
     * 测试redis分片
     */
    @Test
    public void testShards(){
        List<JedisShardInfo> shards = new ArrayList<>();
        String host = "192.168.21.130";
        shards.add(new JedisShardInfo(host,6379));
        shards.add(new JedisShardInfo(host,6380));
        shards.add(new JedisShardInfo(host,6381));

        ShardedJedis jedis = new ShardedJedis(shards);
        jedis.set("redisTest","分片测试");
        System.out.println(jedis.get("redisTest"));

    }

    /**
     * 测试哨兵
     */

    @Test
    public void testSentinel(){
        Set<String> sentinel = new HashSet<>();
        sentinel.add("192.168.21.130:26379");
        JedisSentinelPool pool = new JedisSentinelPool("mymaster",sentinel);
        Jedis jedis = pool.getResource();
        jedis.set("sb","哨兵测试");
        System.out.println(jedis.get("sb"));
    }

    /**
     * 测试redis集群
     */

    @Test
    public void testCluster(){
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.21.130",7000));
        nodes.add(new HostAndPort("192.168.21.130",7001));
        nodes.add(new HostAndPort("192.168.21.130",7002));
        nodes.add(new HostAndPort("192.168.21.130",7003));
        nodes.add(new HostAndPort("192.168.21.130",7004));
        nodes.add(new HostAndPort("192.168.21.130",7005));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        jedisCluster.set("jiqun","集群搭建完成!");
        System.out.println(jedisCluster.get("jiqun"));
    }
}
