package com.lunacia.scorems.controller;


import com.lunacia.scorems.domain.User;
import com.lunacia.scorems.mapper.LoginMapper;
import com.lunacia.scorems.utils.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
public class LoginController {

	@Autowired
	@SuppressWarnings("SpringJavaAutowiringInspection")
	private LoginMapper loginMapper;

	@PostMapping("/api/login")
	//@RequestParam("id") String username, @RequestParam("passwd") String passwd
	public HashMap<String, Object> login(@RequestBody Map<String, Object> resMap) {
		HashMap<String, Object> map = new HashMap<>();
		String username = resMap.get("id").toString();
		String passwd = resMap.get("passwd").toString();
		if (username.equals(passwd)) {
			login(username, passwd, map);
		} else {
			Encode encode = new Encode();
			String secretBytes = encode.MD5(passwd);
			login(username, secretBytes, map);
		}
		return map;
	}

	private void login(String username, String passwd, HashMap<String, Object> map) {
		User user = loginMapper.findById(username);
		if (user == null) {
			map.put("code", 403);
			map.put("message", "登录失败");
			return;
		}
		if (loginMapper.login(username).equals(passwd)) {
			map.put("code", 200);
			map.put("message", "登录成功");
			map.put("isFirst", user.getFlag());
			map.put("isLeader", user.getLeader());
			map.put("count", loginMapper.count(user.getClassNum()));
			map.put("classNum", user.getClassNum());
		} else {
			map.put("code", 403);
			map.put("message", "登录失败");
		}
	}


}
