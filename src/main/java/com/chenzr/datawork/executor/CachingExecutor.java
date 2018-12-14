package com.chenzr.datawork.executor;

import com.chenzr.datawork.mapping.MappedStatement;

import java.util.List;

public class CachingExecutor implements Executor {

    private Executor delegate;

    public CachingExecutor(Executor executor){
        this.delegate = executor;
    }

    @Override
    public int update() {
        return 0;
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter) {
        return delegate.query(ms,parameter);
    }
}
