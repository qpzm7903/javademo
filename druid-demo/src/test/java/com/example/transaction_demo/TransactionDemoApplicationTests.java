package com.example.transaction_demo;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@MapperScan("com.example.transaction_demo.domain.repository")
@EnableTransactionManagement
@Transactional
public class TransactionDemoApplicationTests {

	@Test
	void contextLoads() {
	}

}
