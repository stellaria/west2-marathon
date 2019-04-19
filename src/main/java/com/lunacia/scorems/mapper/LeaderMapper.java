package com.lunacia.scorems.mapper;


import com.lunacia.scorems.domain.Student;
import org.apache.ibatis.annotations.*;
import org.hibernate.validator.constraints.pl.REGON;

import java.util.List;
import java.util.Map;

@Mapper
public interface LeaderMapper {

	/**
	 * 求单科平均分
	 * @param classNum
	 * @return
	 */
	@Select("SELECT CAST(AVG(score) As decimal(9 , 1)) AS avg, sub_name FROM score JOIN sub ON sub.sub_id = score.sub_id " +
			"WHERE class_num = #{classNum} GROUP BY sub.sub_id, sub_name;")
	List<Map<String , Double>> getAllAvg(@Param("classNum") int classNum);

	@Select("SELECT SUM(CASE WHEN score > 60 THEN 1 ELSE 0 END) / COUNT(*) AS pass_rate , sub_name FROM score_view WHERE class_num = #{classNum} GROUP BY sub_name")
	List<Map<String , Object>> getPassRate(@Param("classNum") int classNum);
	//班长获得及格率

	@Select("SELECT SUM(CASE WHEN score > 80 THEN 1 ELSE 0 END) / COUNT(*) AS grate_rate , sub_name FROM score_view WHERE class_num = #{classNum} GROUP BY sub_name")
	List<Map<String , Object>> getGrateRate(@Param("classNum") int classNum);
	//班长获得优秀率

	@Select("SELECT MAX(score) AS max_score , sub_name FROM score_view WHERE class_num = #{classNum} GROUP BY sub_name")
	List<Map<String , Object>> getMax(@Param("classNum") int classNum);

	@Select("SELECT MIN(score) AS min_score , sub_name FROM score_view WHERE class_num = #{classNum} GROUP BY sub_name")
	List<Map<String , Object>> getMin(@Param("classNum") int classNum);

	//获取总分排名
	@Select("SELECT st_id ,total , sum_rank, info_id FROM score_sum WHERE info_id = #{info_id} AND class_num = #{classNum} ORDER BY total DESC")
	@Results(id = "getAllClassRank" , value = {
			@Result(property = "studentNum" , column = "st_id"),
			@Result(property = "classRank" ,column = "sum_rank"),
			@Result(property = "score" , column = "total"),
			@Result(property = "infoId", column = "info_id")
	})
	List<Student> getAllClassRank(@Param("info_id")int infoId, @Param("classNum") int classNum);

	@Update("UPDATE score_sum SET sum_rank = #{sum_rank} WHERE st_id = #{st_id} AND info_id = #{info_id}")
	void setSumRank(@Param("sum_rank") int sumRank, @Param("st_id")String studentNum, @Param("info_id")int infoId);

	/**
	 * 获取某班某科目的所有成绩
	 * @param subId
	 * @param classNum
	 * @return
	 */
	@Select("select score from score where class_num = #{class_num} and sub_id = #{sub_id}")
	List<Integer> getKind(@Param("sub_id")int subId, @Param("class_num")int classNum);

	@Select("SELECT * FROM score WHERE sub_id = #{sub_id} AND class_num = #{class_num} ORDER BY score DESC")
	@Results(id = "getAllSingleRank", value = {
			@Result(property = "studentNum", column = "st_id"),
			@Result(property = "subId", column = "sub_id"),
			@Result(property = "classRank", column = "rank"),
			@Result(property = "score", column = "score")
	})
	List<Student> getAllSingleRank(@Param("class_num")int classNum, @Param("sub_id")int subId);
}
