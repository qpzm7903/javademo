package com.example.transaction_demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = "com.example.transaction_demo")
@MapperScan("com.example.transaction_demo.domain.repository")
@EnableTransactionManagement
@EnableAsync
public class TransactionDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionDemoApplication.class, args);
		
	}

}
