package com.chenzr.datawork.session;


import com.chenzr.datawork.executor.CachingExecutor;
import com.chenzr.datawork.executor.Executor;
import com.chenzr.datawork.executor.SimpleExecutor;
import com.chenzr.datawork.mapping.DefaultMappedStatementFilter;
import com.chenzr.datawork.mapping.MappedStatementFilter;
import com.chenzr.datawork.mapping.XMLMappedStatementFilter;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefaultSessionFactory implements SessionFactory {

    private List<MappedStatementFilter> filters = new ArrayList<MappedStatementFilter>();

    private boolean cacheEnabled = true;

    private DataSource dataSource;

    public DefaultSessionFactory(DataSource _dataSource) {
        this.dataSource = _dataSource;
        filters.add(new XMLMappedStatementFilter());
        filters.add(new DefaultMappedStatementFilter(_dataSource));
    }

    public Session openSession() throws SQLException {
        DefaultSession session = new DefaultSession(filters,newExecutor(dataSource.getConnection()));
        return session;
    }

    public Executor newExecutor(Connection connection) {
        Executor executor = new SimpleExecutor(connection);
        if (cacheEnabled) {
            executor = new CachingExecutor(executor);
        }
        return executor;
    }
}
