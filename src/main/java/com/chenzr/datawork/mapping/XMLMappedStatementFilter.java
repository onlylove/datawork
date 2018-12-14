package com.chenzr.datawork.mapping;

public class XMLMappedStatementFilter implements MappedStatementFilter {

    @Override
    public String getName() {
        return XMLMappedStatementFilter.class.getSimpleName();
    }

    @Override
    public void doFilter(MappedStatement ms, MappedStatementFilterChain chain) {
        chain.doFilter(ms, chain);
    }
}
