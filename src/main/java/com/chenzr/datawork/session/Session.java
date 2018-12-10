package com.chenzr.datawork.session;

import java.io.Closeable;
import java.sql.Connection;
import java.util.List;

public interface Session extends Closeable {

    <T> T selectOne(String statement);

    <E> List<E> selectList(String statement);

    int insert(String statement);

    int delete(String statement);

    int update(String statement);

    void commit();

    void rollback();

    void close();

    Connection getConnection();

}
