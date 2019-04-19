package com.lunacia.scorems.mapper;

import com.lunacia.scorems.domain.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LoginMapper {


	@Select("SELECT password FROM user_password WHERE username=#{id}")
	String login(@Param("id") String id);

	@Select("SELECT * FROM student AS s LEFT JOIN user_password AS u ON s.st_id = u.username" +
			" WHERE st_id=#{id}")
	@Results(id = "findById", value = {
			@Result(property = "studentNum", column = "st_id"),
			@Result(property = "classNum", column = "class_num"),
			@Result(property = "flag", column = "check"),
			@Result(property = "isLeader", column = "leader")
	})
	User findById(@Param("id") String id);

	@Select("SELECT COUNT(*) FROM student WHERE class_num = #{class_num}")
	Integer count(@Param("class_num")int classNum);


}
