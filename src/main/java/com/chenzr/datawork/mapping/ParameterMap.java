package com.chenzr.datawork.mapping;

import java.util.List;

public class ParameterMap {

    private String id;
    private Class<?> type;
    private List<ParameterMapping> parameterMappings;

    private ParameterMap() {
    }

    public String getId() {
        return id;
    }

    public Class<?> getType() {
        return type;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

}
