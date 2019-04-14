package com.lunacia.scorems.mapper;


import com.lunacia.scorems.domain.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {

	@Select("SELECT sub_name, score FROM score_view WHERE st_id=#{studentNum}")
	List<Map<String, Integer>> getScore(@Param("studentNum") String studentNum);

}
