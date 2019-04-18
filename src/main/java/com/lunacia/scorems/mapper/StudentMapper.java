package com.lunacia.scorems.mapper;


import com.lunacia.scorems.domain.Student;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {

	/**
	 * 按学号和学年代码查找某人的成绩
	 * @param studentNum 学号
	 * @param infoID 学年代码
	 * @return 所有成绩
	 */
	@Select("SELECT sub_name, score FROM score_view WHERE st_id=#{studentNum} AND info_id=#{year}")
	List<Map<String, Integer>> getScore(@Param("studentNum") String studentNum, @Param("year") int infoID);

	/**
	 * 获取排名
	 * @param studentNum
	 * @return
	 */
	@Select("SELECT `rank` FROM score WHERE st_id=#{studentNum} AND sub_id=#{subId} AND class_num=#{classNum}")
	Integer getRank(@Param("studentNum")String studentNum, @Param("subId")int subId, @Param("classNum")int classNum);


	@Select("SELECT st_id, sub_id, score, class_num FROM score WHERE class_num = #{classNum} AND sub_id = #{subId} ORDER BY score DESC")
	@Results(id = "getAllScores", value = {
			@Result(property = "studentNum", column = "st_id"),
			@Result(property = "subId", column = "sub_id"),
			@Result(property = "classNum", column = "class_num"),
			@Result(property = "score", column = "score")
	})
	List<Student> getAllScores(@Param("classNum")int classNum, @Param("subId")int subId);

	@Update("UPDATE score SET `rank`=#{rank} WHERE st_id=#{st_id} AND sub_id=#{subId}")
	void setRank(@Param("rank") int rank, @Param("st_id")String studentNum, @Param("subId")int subId);

	//===============================================
	//获取总分排名
	@Select("SELECT sum_rank FROM score_sum WHERE info_id = #{infoId} AND st_id = #{studentNum}")
	Integer getSumRank(@Param("infoId")int infoId , @Param("studentNum")String studentNum);

	@Select("SELECT st_id , info_id , total FROM score_sum WHERE info_id = #{infoId} ORDER BY total DESC")
	@Results(id = "getStudentSum" , value = {
			@Result(property = "studentNum" , column = "st_id") ,
			@Result(property = "infoId" , column = "info_id"),
			@Result(property = "score" , column = "total")
											})
	List<Student> getStudentSum(@Param("infoId") int infoId);

	@Update("UPDATE score_sum SET sum_rank = #{sum_rank} WHERE st_id = #{st_id} AND info_id = #{info_id}" )
	void setSumRank(@Param("sum_rank") int sumRank, @Param("st_id")String studentNum, @Param("info_id")int infoId);

	//==================================================


	@Select("SELECT CAST(AVG(score) AS DECIMAL(9 , 1)) AS avg_score , sub_name FROM score_view WHERE class_num = #{classNum}  GROUP BY sub_name")
	List<Map<String , Double>> getSingleAvg(@Param("classNum") int classNum);
	//学科名加平均成绩

	@Select("SELECT AVG(score) AS avg_score, exam_info.exam_date AS date" +
			" FROM score, exam_info WHERE score.st_id = #{stId} AND score.info_id=#{date_id} AND exam_info.info_id=#{date_id}")
	@Results(id = "getSelfAvg", value = {
			@Result(property = "studentNum", column = "st_id"),
			@Result(property = "score", column = "avg_score"),
			@Result(property = "examDate", column = "date")
	})
	Student getSelfAvg(@Param("stId") String stId, @Param("date_id") int dateId);
	//显示个人成绩平均分



}
