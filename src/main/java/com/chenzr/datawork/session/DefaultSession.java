package com.chenzr.datawork.session;


import com.chenzr.datawork.executor.Executor;
import com.chenzr.datawork.mapping.MappedStatement;

import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class DefaultSession implements Session{

    private Configuration configuration;
    private Executor executor;
    private Connection connection;


    public DefaultSession(Connection _connection){
        this.connection = _connection;
    }

    public <T> T selectOne(String statement,Object object) {
        List<T> list = this.selectList(statement,object);
        if(list.size() == 1){
            return list.get(0);
        }
        return null;
    }

    public <E> List<E> selectList(String statement,Object object) {
        MappedStatement ms = configuration.getMappedStatement(statement);
        return executor.query(ms,wrapCollection(object));
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
