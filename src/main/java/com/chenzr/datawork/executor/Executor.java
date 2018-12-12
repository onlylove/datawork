package com.chenzr.datawork.executor;

import com.chenzr.datawork.mapping.MappedStatement;

import java.util.List;

public interface Executor {

    int update();

    <E> List<E> query(MappedStatement ms,Object parameter);
}
