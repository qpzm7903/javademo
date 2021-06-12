package com.qpzm7903.springmybatisdemo.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @program: javaDemo
 * @description: TODO description
 * @author: qpzm7903
 * @create: 2021-06-12 11:04
 */

@Configuration
public class DataSourceConfig {
    Log logger = LogFactory.getLog(DataSourceConfig.class);

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        logger.info("data source config bean ini");
        return new DataSourceTransactionManager(dataSource);
    }
}
