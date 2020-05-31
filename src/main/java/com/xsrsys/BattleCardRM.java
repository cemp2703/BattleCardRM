package com.xsrsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//Se usó SpringBootServletInitializer para hacer posible la generacion por WAR
@SpringBootApplication
public class BattleCardRM extends SpringBootServletInitializer
{
	public static void main(String[] args) {
		SpringApplication.run(BattleCardRM.class, args);
	}

	//Se sobreescribió este metodo para hacer posible la generación por WAR
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BattleCardRM.class);
	}
	
}
