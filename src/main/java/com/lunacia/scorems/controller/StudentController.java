package com.lunacia.scorems.controller;


import com.lunacia.scorems.domain.Student;
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
	public HashMap<String, Object> getScore(@RequestParam("st_id")String studentNum, @RequestParam("info_id")int infoId) {
		LinkedHashMap<String, Object> hashMap = new LinkedHashMap<>();
		List<Map<String, Integer>> list = new LinkedList<>();
		HashMap<String, Object> data = new HashMap<>();
		data.put("学号", studentNum);
		data.put("score", studentMapper.getScore("031799101", infoId);
		hashMap.put("code", 200);
		hashMap.put("massage", "null");
		hashMap.put("data", data);

		return hashMap;
	}

	@GetMapping("/getRank")
	public HashMap<String, Object> getRank(
			@RequestParam("st_id")String studentNum, @RequestParam("sub_id")int subId, @RequestParam("class_num")int classNum) {
		List<Student> list = null;
		if (studentMapper.getRank(studentNum) == null) {
			list = studentMapper.getAllScores(classNum, subId);
			int rank = 1, increase = 1, prev = 0;
			for (int i = 0; i < list.size(); i++) {
				rank = prev == list.get(i).getScore()? rank : increase;
				increase++;
				prev = list.get(i).getScore();
				list.get(i).setClassRank(rank);
			}
		}
		HashMap<String, Object> map = new HashMap<>();
		HashMap<String, Object> rank = new HashMap<>();
		rank.put("rank", studentMapper.getRank(studentNum));
		map.put("code", 200);
		map.put("message", null);
		map.put("data", rank);
		return map;
	}

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
