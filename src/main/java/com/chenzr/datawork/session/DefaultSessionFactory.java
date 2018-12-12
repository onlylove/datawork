package com.chenzr.datawork.session;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DefaultSessionFactory implements SessionFactory {

    private DataSource dataSource;

    public DefaultSessionFactory(DataSource _dataSource) {
        this.dataSource = _dataSource;
    }

    public Session openSession() throws SQLException {
        DefaultSession session = new DefaultSession(dataSource.getConnection());
        return session;
    }
}
