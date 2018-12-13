package com.chenzr.datawork.executor.resultset;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface ResultSetHandler {

    List<Object> handleResultSets(Statement stmt) throws SQLException;

}
