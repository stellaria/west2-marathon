package com.lunacia.scorems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//@SpringBootApplication
//public class ScoreManageSystemApplication extends SpringBootServletInitializer{
//
//	public static void main(String[] args) {
//		SpringApplication.run(ScoreManageSystemApplication.class, args);
//	}
//
//	@Override
//	protected SpringApplicationBuilder configure (SpringApplicationBuilder builder) {
//		return builder.sources(ScoreManageSystemApplication.class);
//	}
//}

@SpringBootApplication
public class ScoreManageSystemApplication {

	public static void main (String[] args) {
		SpringApplication.run(ScoreManageSystemApplication.class, args);
	}
}