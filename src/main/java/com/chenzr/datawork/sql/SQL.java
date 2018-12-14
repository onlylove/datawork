package com.chenzr.datawork.sql;

import java.util.List;

public interface SQL {

    String getSql();

    List<String> getParameter();

}
