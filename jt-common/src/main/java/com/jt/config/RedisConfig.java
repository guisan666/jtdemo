package com.jt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * redis配置文件
 */
@Configuration    //标识当前类是一个配置类
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {

    /*
    @Value("${redis.host}")
    private String host;
    @Value("${redis.port}")
    private int port;
    */
    /*
    @Bean
    public Jedis jedis(){
        return new Jedis(host,port);
    }
    */

    @Value("${redis.nodes}")
    private String nodes;

    /*
    @Value("${redis.sentinel.masterName}")
    private String masterName;
    */
    /*
    @Value("${redis.sentinels}")
    private String sentinels;
    */
    /*
    @Bean
    public ShardedJedis shardedJedis(){
        List<JedisShardInfo> shardInfos = new ArrayList<>();
        String[] strNodes = nodes.split(",");
        for (String node : strNodes){
            String[] strNode = node.split(":");
            String host = strNode[0];   //根据: 拆分获取主机
            int port = Integer.parseInt(strNode[1]);    //获取端口
            shardInfos.add(new JedisShardInfo(host,port));
        }
        return new ShardedJedis(shardInfos);
    }
    */

    /*
    @Bean
    public JedisSentinelPool jedisSentinelPool(){
        Set<String> sentinel = new HashSet<>();
        sentinel.add(sentinels);
        return new JedisSentinelPool(masterName,sentinel);
    }
    */
    /*
    @Bean
    @Scope("prototype")
    public Jedis jedis(@Qualifier("jedisSentinelPool")JedisSentinelPool pool){
        return pool.getResource();
    }
    */

    /**
     * 集群
     */
    @Bean
    public JedisCluster jedisCluster(){
        Set<HostAndPort> jedis = new HashSet<>();
        String[] node = nodes.split(",");
        for (String strNode : node){
            String[] hostAndPort = strNode.split(":");
            jedis.add(new HostAndPort(hostAndPort[0],Integer.parseInt(hostAndPort[1])));
        }
        JedisCluster jedisCluster = new JedisCluster(jedis);
        return jedisCluster;
    }


}
