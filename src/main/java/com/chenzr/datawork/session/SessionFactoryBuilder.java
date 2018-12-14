package com.chenzr.datawork.session;

import javax.sql.DataSource;

public class SessionFactoryBuilder {

    public static SessionFactory build(DataSource _dataSource) {
        return new DefaultSessionFactory(_dataSource);
    }
}
