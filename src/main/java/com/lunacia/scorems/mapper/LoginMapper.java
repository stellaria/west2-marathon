package com.lunacia.scorems.mapper;

import com.lunacia.scorems.domain.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LoginMapper {


	@Select("SELECT password FROM user_password WHERE username=#{id}")
	String login(@Param("id") String id);

	@Select("SELECT * FROM student WHERE st_id=#{id}")
	@Results(id = "findById", value = {
			@Result(property = "studentNum", column = "st_id"),
			@Result(property = "classNum", column = "class_num")
	})
	User findById(@Param("id") String id);

}
