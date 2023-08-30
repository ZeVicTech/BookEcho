package com.zerolab.bookecho;

import com.zerolab.bookecho.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(AppConfig.class)
@SpringBootApplication
public class BookechoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookechoApplication.class, args);
	}

}
