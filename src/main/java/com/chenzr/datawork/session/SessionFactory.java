package com.chenzr.datawork.session;

import java.sql.SQLException;

public interface SessionFactory {

    public Session openSession() throws SQLException;

}
