package com.chat.HeyYo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.reactive.ReactiveManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {ReactiveSecurityAutoConfiguration.class, ReactiveManagementWebSecurityAutoConfiguration.class })
//@ComponentScan(basePackages = "{com.chat.HeyYo.security, cum.chat.HeyYo.config}")
public class HeyYoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HeyYoApplication.class, args);
	}

}
