package com.lunacia.scorems.controller;


import com.lunacia.scorems.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		data.put("score", studentMapper.getScore("031799101", "1"));
		hashMap.put("code", 200);
		hashMap.put("massage", "null");
		hashMap.put("data", data);

		return hashMap;
	}

//	@GetMapping("/getRank")
//	public HashMap<String, Object> getRank(
//			@RequestParam("st_id")String studentNum, @RequestParam("sub_id")String subId) {
//		HashMap<String, Object> hashMap = new HashMap<>();
//		hashMap.put("code", 200);
//		hashMap.put("message", null);
//		hashMap.put("data", studentMapper.getRank(subId, studentNum));
//		return hashMap;
//	}
//普通学生查看单科平均成绩，

	//普通学生查看自己的平均分
	@GetMapping("/getSelfAvg")
	public LinkedHashMap<String , Object> getSelfAvg(@RequestParam(value = "st_id") String stId){
		HashMap<String , Object> data = new HashMap<>();
		data.put("st_id" , stId);
		data.put("self_avg" , studentMapper.getSelfAvg(stId));
		LinkedHashMap <String , Object> hashMap = new LinkedHashMap<>();
		hashMap.put("data" , data);

		return hashMap;
	}




}
