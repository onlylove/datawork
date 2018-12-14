package com.chenzr.datawork.mapping;

public interface MappedStatementFilter {

    String getName();

    void doFilter(MappedStatement ms, MappedStatementFilterChain chain);

}
