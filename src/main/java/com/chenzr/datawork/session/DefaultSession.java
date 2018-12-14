package com.chenzr.datawork.session;


import com.chenzr.datawork.executor.Executor;
import com.chenzr.datawork.mapping.MappedStatement;
import com.chenzr.datawork.mapping.MappedStatementFilter;
import com.chenzr.datawork.mapping.MappedStatementFilterChain;

import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class DefaultSession implements Session {

    private MappedStatementFilterChain filterChain = new MappedStatementFilterChain();
    private Executor executor;


    public DefaultSession(List<MappedStatementFilter> filters,Executor _executor) {
        this.executor = _executor;
        for (MappedStatementFilter f : filters){
            filterChain.addFilter(f);
        }
    }

    public <T> T selectOne(String statement, Object object) {
        List<T> list = this.selectList(statement, object);
        if (list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    public <E> List<E> selectList(String statement, Object object) {
        MappedStatement ms = new MappedStatement(statement);
        filterChain.doFilter(ms,filterChain);
        return executor.query(ms, wrapCollection(object));
    }

    public int insert(String statement) {
        return 0;
    }

    public int delete(String statement) {
        return 0;
    }

    public int update(String statement) {
        return 0;
    }

    public void commit() {

    }

    public void rollback() {

    }

    public void close() {

    }

    public Connection getConnection() {
        return null;
    }

    private Object wrapCollection(final Object object) {
        if (object instanceof Collection) {
            HashMap map = new HashMap();
            map.put("collection", object);
            if (object instanceof List) {
                map.put("list", object);
            }
            return map;
        } else if (object != null && object.getClass().isArray()) {
            HashMap map = new HashMap();
            map.put("array", object);
            return map;
        }
        return object;
    }

}
