package com.jt.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.service.UserService;
import redis.clients.jedis.JedisCluster;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private JedisCluster jedisCluster;

	/**
	 * 经过跨域请求返回系统数据 data:true  已经存在  false不存在
	 */

	@RequestMapping("/check/{param}/{type}")
	public JSONPObject findCheckUser(String callback , @PathVariable String param,@PathVariable Integer type){

		JSONPObject jsonpObject = null;
		try {
			boolean flag = userService.findCheckUser(param,type);
			jsonpObject = new JSONPObject(callback, SysResult.success(flag));
		}catch (Exception e){
			jsonpObject = new JSONPObject(callback,SysResult.fail());
			e.printStackTrace();
		}
		return jsonpObject;
	}

	@RequestMapping("/query/{token}")
	public JSONPObject findUserByToken(String callback, @PathVariable String token){
		String userJson = jedisCluster.get(token);
		JSONPObject jsonpObject = null;
		if (StringUtils.isEmpty(userJson)){
			jsonpObject = new JSONPObject(callback,SysResult.fail());
		}else{
			jsonpObject = new JSONPObject(callback,SysResult.success(userJson));
		}
		return jsonpObject;
	}

}
