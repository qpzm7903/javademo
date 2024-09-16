package com.example.transaction_demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionExecution;
import org.springframework.transaction.TransactionExecutionListener;

@Component
@Slf4j
public class TransactionListener implements TransactionExecutionListener {
    
    @Override
    public void beforeBegin(TransactionExecution transaction) {
        log.info("before begin, {} ", transaction.getTransactionName());
    }
    
    @Override
    public void afterBegin(TransactionExecution transaction, Throwable beginFailure) {
        log.info("after begin,{}", transaction.getTransactionName());
    }
    
    @Override
    public void beforeCommit(TransactionExecution transaction) {
        log.info("before commit,{} ", transaction.getTransactionName());
    }
    
    @Override
    public void afterCommit(TransactionExecution transaction, Throwable commitFailure) {
        log.info("after commit, {}", transaction.getTransactionName());
    }
    
    @Override
    public void beforeRollback(TransactionExecution transaction) {
        log.info("before rollback, {} ", transaction.getTransactionName());
    }
    
    @Override
    public void afterRollback(TransactionExecution transaction, Throwable rollbackFailure) {
        log.info("after rollback, {}",transaction.getTransactionName());
    }
}
