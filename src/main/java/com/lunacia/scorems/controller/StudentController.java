package com.lunacia.scorems.controller;


import com.lunacia.scorems.domain.Student;
import com.lunacia.scorems.mapper.StudentMapper;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
public class StudentController {

	@Autowired
	@SuppressWarnings("SpringJavaAutowiringInspection")
	private StudentMapper studentMapper;

	/**
	 * 获得某个学生某次考试的所有成绩
	 * @param studentNum
	 * @param infoId
	 * @return
	 */
	@GetMapping("/api/score/self")
	public HashMap<String, Object> getScore(@RequestParam("st_id")String studentNum, @RequestParam("info_id")int infoId) {
		LinkedHashMap<String, Object> hashMap = new LinkedHashMap<>();
		List<Map<String, Integer>> list = new LinkedList<>();
		HashMap<String, Object> data = new HashMap<>();
		data.put("st_id", studentNum);
		data.put("score", studentMapper.getScore(studentNum, infoId));
		hashMap.put("code", 200);
		hashMap.put("massage", "");
		hashMap.put("data", data);

		return hashMap;
	}

	/**
	 * 返回学生某项考试的班级排名
	 * @param studentNum
	 * @param subId
	 * @param classNum
	 * @return rank
	 */
	@GetMapping("/api/rank/self")
	public HashMap<String, Object> getRank(
			@RequestParam("st_id")String studentNum, @RequestParam("sub_id")int subId, @RequestParam("class_num")int classNum) {
		List<Student> list = null;
		if (studentMapper.getRank(studentNum, subId, classNum) == null) {
			list = studentMapper.getAllScores(classNum, subId);
			int rank = 1, increase = 1, prev = 0;
			for (int i = 0; i < list.size(); i++) {
				rank = prev == list.get(i).getScore()? rank : increase;
				increase++;
				prev = list.get(i).getScore();
				list.get(i).setClassRank(rank);
				studentMapper.setRank(rank, list.get(i).getStudentNum(), subId);
			}
		}
		HashMap<String, Object> map = new HashMap<>();
		HashMap<String, Object> rank = new HashMap<>();
		rank.put("rank", studentMapper.getRank(studentNum, subId, classNum));
		map.put("code", 200);
		map.put("message", "");
		map.put("data", rank);
		return map;
	}

	/**
	 * 获取某人某学期总分排名
	 * @param studentNum
	 * @param infoId
	 * @param classNum
	 * @return
	 */
	@GetMapping("/api/rank/sum")
	public HashMap<String , Object> getSumRank(
			@RequestParam("st_id")String studentNum ,
			@RequestParam("info_id")int infoId,
			@RequestParam("class_num")int classNum){
		List<Student> list = studentMapper.getStudentSum(infoId);
		if(studentMapper.getSumRank(infoId,studentNum,classNum) == 0){
			int rank = 1 , increase = 1 , prev = 0;
			for (int i = 0 ; i < list.size() ; i++){
				rank = prev == list.get(i).getScore()? rank : increase;
				increase++;
				prev = list.get(i).getScore();
				list.get(i).setClassRank(rank);
				studentMapper.setSumRank(rank , list.get(i).getStudentNum() , list.get(i).getInfoId());
			}
		}
		HashMap<String , Object> hashMap = new HashMap<>();
		HashMap<String , Object> rank = new HashMap<>();
		rank.put("sum_rank" , studentMapper.getSumRank(infoId,studentNum,classNum));
		hashMap.put("code", 200);
		hashMap.put("message", "");
		hashMap.put("data", rank);
		return hashMap;
	}


	/**
	 * 返回某个学生某次考试所有科目的平均分
	 * @param stId
	 * @param dateId
	 * @return
	 */
	@GetMapping("/api/avg/self")
	public LinkedHashMap<String , Object> getSelfAvg(@RequestParam(value = "st_id") String stId,
	                                                 @RequestParam(value = "exam_info")int dateId){
		HashMap<String , Object> data = new HashMap<>();
		LinkedHashMap <String , Object> hashMap = new LinkedHashMap<>();
		Student student = studentMapper.getSelfAvg(stId, dateId);
		if (student != null) {
			data.put("st_id" , stId);
			data.put("self_avg" , student.getScore());
			data.put("exam_date", student.getExamDate());
			hashMap.put("code", 200);
			hashMap.put("message", "");
			hashMap.put("data" , data);
		} else {
			hashMap.put("code", 400);
			hashMap.put("message", "查无此人");
		}
		return hashMap;
	}


}
