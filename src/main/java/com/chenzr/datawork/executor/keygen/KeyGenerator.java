package com.chenzr.datawork.executor.keygen;

import com.chenzr.datawork.executor.Executor;
import com.chenzr.datawork.mapping.MappedStatement;

import java.sql.Statement;

public interface KeyGenerator {

    void processBefore(Executor executor, MappedStatement ms, Statement stmt, Object parameter);

    void processAfter(Executor executor, MappedStatement ms, Statement stmt, Object parameter);

}