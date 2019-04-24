package com.hmx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//@SpringBootApplication
//@MapperScan(value = {"com.hmx.*.dao"})
//public class MyApplication {
//
//	public static void main(String[] args){
//		SpringApplication.run(MyApplication.class, args);
//	}
//}

@SpringBootApplication
@MapperScan(value = {"com.hmx.*.dao"})
public class MyApplication extends SpringBootServletInitializer{
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(MyApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return super.configure(builder);
	}
}

