package com.javacloud.assetmanagementsystem.cloud;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AssetManagementSystemCloudUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssetManagementSystemCloudUserApplication.class, args);
	}
	
	
	@Bean
	public PasswordEncoder getPassword() {
		return new BCryptPasswordEncoder();
	}

}
