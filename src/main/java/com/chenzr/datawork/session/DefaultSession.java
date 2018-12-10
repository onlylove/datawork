package com.chenzr.datawork.session;

import java.sql.Connection;
import java.util.List;

public class DefaultSession implements Session{
    @Override
    public <T> T selectOne(String statement) {
        return null;
    }

    @Override
    public <E> List<E> selectList(String statement) {
        return null;
    }

    @Override
    public int insert(String statement) {
        return 0;
    }

    @Override
    public int delete(String statement) {
        return 0;
    }

    @Override
    public int update(String statement) {
        return 0;
    }

    @Override
    public void commit() {

    }

    @Override
    public void rollback() {

    }

    @Override
    public void close() {

    }

    @Override
    public Connection getConnection() {
        return null;
    }
}
