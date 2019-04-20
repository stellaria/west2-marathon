package com.lunacia.scorems.controller;


import com.lunacia.scorems.domain.Student;
import com.lunacia.scorems.mapper.LeaderMapper;
import com.lunacia.scorems.mapper.StudentMapper;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@CrossOrigin
@RestController
public class LeaderController {

	@Autowired
	@SuppressWarnings("SpringJavaAutowiringInspection")
	private LeaderMapper leaderMapper;
	@Autowired
	private StudentMapper studentMapper;

	@GetMapping("/api/getLeader")
	public LinkedHashMap<String , Object> getLeader(@RequestParam(value = "class_num") int classNum){
		LinkedHashMap<String , Object> hashMap = new LinkedHashMap<>();
		HashMap<String , Object> data = new HashMap<>();
		hashMap.put("code", 200);
		hashMap.put("message", "");
		data.put("class_num" , classNum);
		data.put("avg_score" , leaderMapper.getAllAvg(classNum));
		data.put("pass_rate" , leaderMapper.getPassRate(classNum));
		data.put("grate_rate" , leaderMapper.getGrateRate(classNum));
		data.put("max" , leaderMapper.getMax(classNum));
		data.put("min" , leaderMapper.getMin(classNum));
		hashMap.put("data" , data);
		return hashMap;
	}

	/*
	@GetMapping("/getAvg/getAll")
	public LinkedHashMap<String, Object> getSingleAvg(@RequestParam(value = "class_num") int classNum){
		LinkedHashMap<String , Object> hashMap = new LinkedHashMap<>();
		HashMap<String, Object> data = new HashMap<>();
		data.put("class_num" , classNum);
		data.put("avg_score" , leaderMapper.getAllAvg(classNum));
		hashMap.put("data" , data);
		return hashMap;
	}


	//获得及格率
	@GetMapping("/getPassRate")
	public LinkedHashMap<String , Object> getPassRate(@RequestParam(value = "class_num") int classNum){
		HashMap<String ,Object> data = new HashMap<>();
		data.put("class_num" , classNum);
		data.put("pass_rate" , leaderMapper.getPassRate(classNum));
		LinkedHashMap<String , Object> hashMap = new LinkedHashMap<>();
		hashMap.put("data" , data);

		return hashMap;
	}

	@GetMapping("/getGrateRate")
	public LinkedHashMap<String , Object> getGrateRate(@RequestParam(value = "class_num") int classNum){
		HashMap<String ,Object> data = new HashMap<>();
		data.put("class_num" , classNum);
		data.put("grate_rate" , leaderMapper.getGrateRate(classNum));
		LinkedHashMap<String , Object> hashMap = new LinkedHashMap<>();
		hashMap.put("data" , data);

		return hashMap;
	}

	@GetMapping("/getMax")
	public LinkedHashMap<String , Object> getMax(@RequestParam(value = "class_num") int classNum){
		HashMap<String , Object> data = new HashMap<>();
		data.put("class_num" , classNum);
		data.put("max" , leaderMapper.getMax(classNum));
		LinkedHashMap<String , Object> hashMap = new LinkedHashMap<>();
		hashMap.put("data" , data);

		return hashMap;
	}

	@GetMapping("/getMin")
	public LinkedHashMap<String , Object> getMin(@RequestParam(value = "class_num") int classNum){
		HashMap<String , Object> data = new HashMap<>();
		data.put("class_num" , classNum);
		data.put("min" , leaderMapper.getMin(classNum));
		LinkedHashMap<String , Object> hashMap = new LinkedHashMap<>();
		hashMap.put("data" , data);

		return hashMap;
	}
	*/

	/**
	 * 获取某个学期所有人的总分排名
	 * @param classMum
	 * @param infoId
	 * @return
	 */
	@GetMapping("/api/rank/all")
	public HashMap<String , Object> getAllClassRank(@RequestParam(value = "class_num") int classMum,
	                                                @RequestParam(value = "info_id") int infoId){
		List<Student> list = leaderMapper.getAllClassRank(infoId, classMum);
		if (list.get(0).getClassRank() == 0){
			int rank = 1 , increase = 1 , prev = 0;
			for(int i = 0 ; i < list.size() ; i++){
				rank = prev == list.get(i).getScore()? rank : increase;
				increase++;
				prev = list.get(i).getScore();
				list.get(i).setClassRank(rank);
				leaderMapper.setSumRank(rank , list.get(i).getStudentNum() , list.get(i).getInfoId());
			}
		}
		for (int i = 0 ; i< list.size() ; i++){
			HashMap<String , Object> allScore = new HashMap<>();
			allScore.put("AllScore" , studentMapper.getScore(list.get(i).getStudentNum() , infoId));
			list.get(i).setAllScore(allScore);
			list.get(i).setStudentName(leaderMapper.getStudentName(list.get(i).getStudentNum()));
		}

		HashMap<String , Object> hashMap = new HashMap<>();
		hashMap.put("data" , list);
		hashMap.put("sub_name" , leaderMapper.getSubIdByInfoId(infoId));
		hashMap.put("code", 200);
		hashMap.put("message", "");
		return hashMap;
	}

	/**
	 * 获取本班某科目所有人的排名
	 * 以这个科目进行排名
	 * @param classNum
	 * @param subId
	 * @return
	 */
	@GetMapping("/api/rank/single")
	public HashMap<String, Object> getAllSingleRank(@RequestParam(value = "class_num")int classNum,
	                                                @RequestParam(value = "sub_id")int subId) {
		HashMap<String, Object> map = new HashMap<>();
		List<Student> list = leaderMapper.getAllSingleRank(classNum, subId);
		if (list.get(0).getClassRank() == 0) {
			int rank = 1, increase = 1, prev = 0;
			for (int i = 0; i < list.size(); i++) {
				rank = prev == list.get(i).getScore()? rank : increase;
				increase++;
				prev = list.get(i).getScore();
				list.get(i).setClassRank(rank);
				studentMapper.setRank(rank, list.get(i).getStudentNum(), subId);
			}
		}
		for(int i = 0 ; i < list.size() ; i++){
			HashMap<String , Object> allScore = new HashMap<>();
			allScore.put("AllScore" , studentMapper.getScore(list.get(i).getStudentNum() ,list.get(i).getInfoId()));
			list.get(i).setAllScore(allScore);
			list.get(i).setScore(leaderMapper.getTotal(list.get(i).getStudentNum() ,list.get(i).getInfoId()));
			list.get(i).setStudentName(leaderMapper.getStudentName(list.get(i).getStudentNum()));
		}
		map.put("code", 200);
		map.put("message", "");
		map.put("data", list);
		map.put("sub_name" , leaderMapper.getSubIdByInfoId(list.get(1).getInfoId()));
		map.put("class_num", classNum);

		return map;
	}

	/**
	 * 获取某科目的优、良、及格和不及格的人数
	 * @param classNum
	 * @param subId
	 * @return
	 */
	@GetMapping("/api/score/classify")
	public HashMap<String, Object> getClassify(@RequestParam(value = "class_num") int classNum,
	                                           @RequestParam(value = "sub_id")int subId) {
		List<Integer> scoreList = leaderMapper.getKind(classNum, subId);
		int grate = 0, well = 0, pass = 0, unpass = 0;
		for (int i = 0; i < scoreList.size(); i++) {
			if (scoreList.get(i) >= 85)
				grate++;
			else if (scoreList.get(i) >= 70 && scoreList.get(i) < 85)
				well++;
			else if (scoreList.get(i) >= 60 && scoreList.get(i) < 70)
				pass++;
			else
				unpass++;
		}
		HashMap<String, Object>map = new HashMap<>();
		HashMap<String, Object>data = new HashMap<>();
		map.put("code", 200);
		map.put("message", "");
		map.put("class_num", classNum);
		map.put("sub_id", subId);
		data.put("grate", grate);
		data.put("well", well);
		data.put("pass", pass);
		data.put("unpass", unpass);
		map.put("data", data);
		return map;
	}


}
