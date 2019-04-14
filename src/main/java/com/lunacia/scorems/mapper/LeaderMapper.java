package com.lunacia.scorems.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
}
