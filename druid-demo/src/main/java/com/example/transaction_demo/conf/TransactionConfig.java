package com.example.transaction_demo.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.interceptor.CompositeTransactionAttributeSource;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Properties;

/**
 * not work
 */
// @Configuration
public class TransactionConfig {
    
    @Bean
    // @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public TransactionAttributeSource transactionAttributeSource() {
        AnnotationTransactionAttributeSource annotationTransactionAttributeSource = new AnnotationTransactionAttributeSource();
        NameMatchTransactionAttributeSource nameMatchTransactionAttributeSource = new NameMatchTransactionAttributeSource();
        Properties txAttributes = new Properties();
        txAttributes.setProperty("save*", "PROPAGATION_REQUIRED");
        txAttributes.setProperty("update*", "PROPAGATION_REQUIRED");
        txAttributes.setProperty("*", "PROPAGATION_SUPPORTS,readOnly"); // 默认其他方法为只读
        nameMatchTransactionAttributeSource.setProperties(txAttributes);
        CompositeTransactionAttributeSource compositeTransactionAttributeSource = new CompositeTransactionAttributeSource(
                annotationTransactionAttributeSource, nameMatchTransactionAttributeSource);
        
        return compositeTransactionAttributeSource;
    }
    
    @Bean
    // @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public TransactionInterceptor transactionInterceptor(TransactionAttributeSource transactionAttributeSource) {
        TransactionInterceptor interceptor = new TransactionInterceptor();
        interceptor.setTransactionAttributeSource(transactionAttributeSource);
        return interceptor;
    }
}
