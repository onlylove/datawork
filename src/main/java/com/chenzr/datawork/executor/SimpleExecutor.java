package com.chenzr.datawork.executor;

import com.chenzr.datawork.executor.resultset.ResultSetHandler;
import com.chenzr.datawork.mapping.MappedStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class SimpleExecutor extends BaseExecutor {

    public SimpleExecutor(Connection _connection) {
        super(_connection);
    }

    @Override
    protected <E> List<E> doQuery(MappedStatement ms, Object parameter, ResultSetHandler resultSetHandler) throws SQLException {
        String sql= ms.getSql();
        System.out.println(sql);
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.execute();
        List<E> list = (List<E>) resultSetHandler.handleResultSets(pst);
        pst.close();
        return list;
    }

}
