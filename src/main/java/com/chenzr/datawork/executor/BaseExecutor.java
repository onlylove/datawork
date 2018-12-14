package com.chenzr.datawork.executor;

import com.chenzr.datawork.executor.result.ResultHandler;
import com.chenzr.datawork.mapping.MappedStatement;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseExecutor implements Executor {

    protected Executor wrapper;
    private Connection connection;

    public BaseExecutor(Connection _connection) {
        this.connection = _connection;
        this.wrapper = this;
    }


    @Override
    public int update() {
        return 0;
    }

    @Override
    public <E> List<E> query(MappedStatement ms, Object parameter) {
        try {
            return doQuery(ms,parameter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected abstract <E> List<E> doQuery(MappedStatement ms, Object parameter) throws SQLException;


}
