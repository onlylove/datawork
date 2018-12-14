package com.chenzr.datawork.executor;

import com.chenzr.datawork.mapping.MappedStatement;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SimpleExecutor extends BaseExecutor {
    public SimpleExecutor(Connection _connection) {
        super(_connection);
    }

    @Override
    protected <E> List<E> doQuery(MappedStatement ms, Object parameter) throws SQLException {
        String sql= ms.getSql();
        return null;
    }
}
