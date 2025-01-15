package com.example.transaction_demo;

import com.example.transaction_demo.domain.model.Product;
import com.example.transaction_demo.domain.repository.ProductRepo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// @Component
public class DruidTest {
    @Autowired
    private ApplicationContext applicationContext;
    
    @Autowired
    private ProductRepo productRepo;
    
    private final static ThreadPoolExecutor POOL_EXECUTOR = new ThreadPoolExecutor(1, 1, 100, TimeUnit.HOURS,
            new LinkedBlockingQueue<Runnable>());
    
    // get spring context 
    
    public String testSql() {
        Object dataSource = applicationContext.getBean("dataSource");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.addMixIn(Object.class, IgnoreLogWriterMixin.class);
        try {
            String dataSourceJson = objectMapper.writeValueAsString(dataSource);
            System.out.println("DataSource configuration: " + dataSourceJson);
        } catch (Exception e) {
            System.err.println("Failed to serialize dataSource: " + e.getMessage());
        }
        // use objectmapper parse datssource into a jsonobjec and log it 
        
        long startTime = System.currentTimeMillis();
        List<Product> productList = productRepo.listAllProduct();
        long endTime = System.currentTimeMillis();
        System.out.println("Query took: " + (endTime - startTime) + " ms");
        System.out.println(productList);
        return "";
    }
    
    @PostConstruct
    public void init() {
        Runnable runnable = this::asyncTestSql;
        POOL_EXECUTOR.execute(runnable);
        
    }
    
    @Async
    public void asyncTestSql() {
        testSql();
    }
}

@JsonIgnoreProperties({"logWriter", "driver", "connection", "pooledConnection", "parentLogger", "initStackTrace", "dataSourceStat", "statData", "statDataForMBean", "statValueAndReset","sqlStatMap","compositeData"})
abstract class IgnoreLogWriterMixin {
}
