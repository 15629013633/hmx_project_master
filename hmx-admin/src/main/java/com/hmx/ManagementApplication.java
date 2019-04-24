package com.hmx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;

//@SpringBootApplication
//@MapperScan(value = {"com.hmx.*.dao"})
//@EnableAsync
//public class ManagementApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(ManagementApplication.class, args);
//	}
//
//}
@SpringBootApplication
@MapperScan(value = {"com.hmx.*.dao"})
public class ManagementApplication extends SpringBootServletInitializer{
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(ManagementApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return super.configure(builder);
	}
}
