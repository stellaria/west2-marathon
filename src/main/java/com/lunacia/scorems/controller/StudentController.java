package com.lunacia.scorems.controller;


import com.lunacia.scorems.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class StudentController {

	@Autowired
	private StudentMapper studentMapper;

	@GetMapping("/getScore")
	public HashMap<String, Object> getScore() {
		LinkedHashMap<String, Object> hashMap = new LinkedHashMap<>();
		List<Map<String, Integer>> list = new LinkedList<>();
		HashMap<String, Object> data = new HashMap<>();
		data.put("学号", "031799101");
		data.put("score", studentMapper.getScore("031799101"));
		hashMap.put("code", 200);
		hashMap.put("massage", "null");
		hashMap.put("data", data);

		return hashMap;
	}
}
