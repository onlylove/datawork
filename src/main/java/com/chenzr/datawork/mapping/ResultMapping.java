package com.chenzr.datawork.mapping;

import com.chenzr.datawork.type.JdbcType;
import com.chenzr.datawork.type.TypeHandler;

public class ResultMapping {

    private String property;
    private String column;
    private Class<?> javaType;
    private JdbcType jdbcType;
    private TypeHandler<?> typeHandler;
    private String columnPrefix;
    private ResultFlag flag;
    private String nestedResultMapId;
    private String joinTable;
    private String joinColumn;
    private String foreignColumn;

    public ResultMapping() {
    }

    public ResultMapping(String property,String column,Class<?> javaType,JdbcType jdbcType,TypeHandler<?> typeHandler,
                         String columnPrefix,ResultFlag flag,String nestedResultMapId,String foreignColumn,String joinTable,String joinColumn) {
        this.property = property;
        this.column = column;
        this.javaType = javaType;
        this.jdbcType = jdbcType;
        this.typeHandler = typeHandler;
        this.columnPrefix = columnPrefix;
        this.flag = flag;
        this.nestedResultMapId = nestedResultMapId;
        this.foreignColumn = foreignColumn;
        this.joinColumn = joinColumn;
        this.joinTable = joinTable;
    }

    public String getProperty() {
        return property;
    }

    public String getColumn() {
        return column;
    }

    public Class<?> getJavaType() {
        return javaType;
    }

    public JdbcType getJdbcType() {
        return jdbcType;
    }

    public TypeHandler<?> getTypeHandler() {
        return typeHandler;
    }

    public String getNestedResultMapId() {
        return nestedResultMapId;
    }


    public String getColumnPrefix() {
        return columnPrefix;
    }

    public ResultFlag getFlag() {
        return flag;
    }

    public String getJoinTable() {
        return joinTable;
    }

    public String getJoinColumn() {
        return joinColumn;
    }

    public String getForeignColumn() {
        return foreignColumn;
    }

    public void setColumnPrefix(String columnPrefix) {
        this.columnPrefix = columnPrefix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResultMapping that = (ResultMapping) o;

        if (property == null || !property.equals(that.property)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        if (property != null) {
            return property.hashCode();
        } else if (column != null) {
            return column.hashCode();
        } else {
            return 0;
        }
    }


}
