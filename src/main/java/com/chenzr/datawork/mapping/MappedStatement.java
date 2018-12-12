package com.chenzr.datawork.mapping;


import com.chenzr.datawork.executor.keygen.KeyGenerator;

import java.util.List;

public final class MappedStatement {

    private String id;
    private Integer fetchSize;
    private Integer timeout;
//    private SqlSource sqlSource;
    private ParameterMap parameterMap;
    private List<ResultMap> resultMaps;
    private boolean flushCacheRequired;
    private boolean useCache;
    private boolean resultOrdered;
    private CommandType sqlCommandType;
    private KeyGenerator keyGenerator;
    private String[] keyProperties;
    private String[] keyColumns;
    private boolean hasNestedResultMaps;
    private String databaseId;
    private String[] resultSets;

}
