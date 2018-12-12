package com.chenzr.datawork.mapping;


import java.util.List;
import java.util.Set;

public class ResultMap {
    private String id;
    private Class<?> type;
    private List<ResultMapping> resultMappings;
    private List<ResultMapping> idResultMappings;
    private List<ResultMapping> propertyResultMappings;
    private Set<String> mappedColumns;
    private boolean hasNestedResultMaps;

    public ResultMap() {
    }

    public ResultMap(String id,Class<?> type,List<ResultMapping> resultMappings,List<ResultMapping> idResultMappings,List<ResultMapping> propertyResultMappings,Set<String> mappedColumns,boolean hasNestedResultMaps) {
        this.id = id;
        this.type = type;
        this.resultMappings = resultMappings;
        this.idResultMappings = idResultMappings;
        this.propertyResultMappings = propertyResultMappings;
        this.mappedColumns = mappedColumns;
        this.hasNestedResultMaps = hasNestedResultMaps;
    }

    public String getId() {
        return id;
    }

    public boolean hasNestedResultMaps() {
        return hasNestedResultMaps;
    }

    public Class<?> getType() {
        return type;
    }

    public List<ResultMapping> getResultMappings() {
        return resultMappings;
    }

    public List<ResultMapping> getPropertyResultMappings() {
        return propertyResultMappings;
    }

    public List<ResultMapping> getIdResultMappings() {
        return idResultMappings;
    }

    public Set<String> getMappedColumns() {
        return mappedColumns;
    }

    public void forceNestedResultMaps() {
        hasNestedResultMaps = true;
    }


}
