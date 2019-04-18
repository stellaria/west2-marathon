package com.lunacia.scorems.controller;


import com.lunacia.scorems.domain.Student;
import com.lunacia.scorems.mapper.LeaderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
public class LeaderController {

	@Autowired
	private LeaderMapper leaderMapper;

	@GetMapping("/avg/all")
	public LinkedHashMap<String, Object> getSingleAvg(@RequestParam(value = "class_num") int classNum){
		LinkedHashMap<String , Object> hashMap = new LinkedHashMap<>();
		HashMap<String, Object> data = new HashMap<>();
		List<?> scoreList = leaderMapper.getAllAvg(classNum);
		if (scoreList.size() != 0) {
			hashMap.put("code", 200);
			hashMap.put("message", "");
			data.put("sub_id" , classNum);
			data.put("avg_score" ,scoreList);
			hashMap.put("data" , data);
		} else {
			hashMap.put("code", 400);
			hashMap.put("message", "无此班级");
		}

		return hashMap;
	}


	//获得及格率
	@GetMapping("/rate/pass")
	public LinkedHashMap<String , Object> getPassRate(@RequestParam(value = "class_num") int classNum){
		HashMap<String ,Object> data = new HashMap<>();
		LinkedHashMap<String , Object> hashMap = new LinkedHashMap<>();
		List<?> rateList = leaderMapper.getPassRate(classNum);
		hashMap.put("code", 200);
		hashMap.put("message", "");
		data.put("class_num" , classNum);
		data.put("pass_rate" , rateList);
		hashMap.put("data" , data);
		return hashMap;
	}

	@GetMapping("/rate/grate")
	public LinkedHashMap<String , Object> getGrateRate(@RequestParam(value = "class_num") int classNum){
		LinkedHashMap<String , Object> hashMap = new LinkedHashMap<>();
		HashMap<String, Object> data = new HashMap<>();
		List<?> grateList = leaderMapper.getPassRate(classNum);
		hashMap.put("code", 200);
		hashMap.put("message", "");
		data.put("class_num" , classNum);
		data.put("pass_rate" , grateList);
		hashMap.put("data" , data);
		return hashMap;
	}

	@GetMapping("/getMax")
	public LinkedHashMap<String , Object> getMax(@RequestParam(value = "class_num") int classNum){
		HashMap<String , Object> data = new HashMap<>();
		data.put("class_num" , classNum);
		data.put("max" , leaderMapper.getMax(classNum));
		LinkedHashMap<String , Object> hashMap = new LinkedHashMap<>();
		hashMap.put("code", 200);
		hashMap.put("message", "");
		hashMap.put("data" , data);

		return hashMap;
	}

	@GetMapping("/getMin")
	public LinkedHashMap<String , Object> getMin(@RequestParam(value = "class_num") int classNum){
		HashMap<String , Object> data = new HashMap<>();
		data.put("class_num" , classNum);
		data.put("min" , leaderMapper.getMin(classNum));
		LinkedHashMap<String , Object> hashMap = new LinkedHashMap<>();
		hashMap.put("code", 200);
		hashMap.put("message", "");
		hashMap.put("data" , data);

		return hashMap;
	}

	@GetMapping("/rank/all")
	public HashMap<String , Object> getAllClassRank(@RequestParam(value = "class_num") int classMum ,
													@RequestParam(value = "info_id") int infoId){
		List<Student> list = null;
		if (leaderMapper.checkNull() == null){
			int rank = 1 , increase = 1 , prev = 0;
			list = leaderMapper.getAllClassRank(infoId , classMum);
			for(int i = 0 ; i < list.size() ; i++){
				rank = prev == list.get(i).getScore()? rank : increase;
				increase++;
				prev = list.get(i).getScore();
				//list.get(i).setClassRank(rank);
				leaderMapper.setSumRank(rank , list.get(i).getStudentNum() , list.get(i).getInfoId());
			}
		}
		HashMap<String , Object> hashMap = new HashMap<>();
		HashMap<String , List<Student>> rank = new HashMap<>();
		rank.put("sum_rank" , leaderMapper.getAllClassRank(infoId , classMum));
		hashMap.put("code", 200);
		hashMap.put("message", "");
		hashMap.put("data", rank);
		return hashMap;
	}
}
