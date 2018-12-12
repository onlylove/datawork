package com.chenzr.datawork.mapping;


import com.chenzr.datawork.type.JdbcType;
import com.chenzr.datawork.type.TypeHandler;

import java.sql.ResultSet;

public class ParameterMapping {

    private String property;
    private Class<?> javaType = Object.class;
    private JdbcType jdbcType;
    private Integer numericScale;
    private TypeHandler<?> typeHandler;
    private String resultMapId;
    private String jdbcTypeName;
    private String expression;

    private ParameterMapping() {
    }


    public String getProperty() {
        return property;
    }

    /**
     * Used for handling output of callable statements
     * @return
     */
    public Class<?> getJavaType() {
        return javaType;
    }

    /**
     * Used in the UnknownTypeHandler in case there is no handler for the property type
     * @return
     */
    public JdbcType getJdbcType() {
        return jdbcType;
    }

    /**
     * Used for handling output of callable statements
     * @return
     */
    public Integer getNumericScale() {
        return numericScale;
    }

    /**
     * Used when setting parameters to the PreparedStatement
     * @return
     */
    public TypeHandler<?> getTypeHandler() {
        return typeHandler;
    }

    /**
     * Used for handling output of callable statements
     * @return
     */
    public String getResultMapId() {
        return resultMapId;
    }

    /**
     * Used for handling output of callable statements
     * @return
     */
    public String getJdbcTypeName() {
        return jdbcTypeName;
    }

    /**
     * Not used
     * @return
     */
    public String getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ParameterMapping{");
        //sb.append("configuration=").append(configuration); // configuration doesn't have a useful .toString()
        sb.append("property='").append(property).append('\'');
        sb.append(", javaType=").append(javaType);
        sb.append(", jdbcType=").append(jdbcType);
        sb.append(", numericScale=").append(numericScale);
        //sb.append(", typeHandler=").append(typeHandler); // typeHandler also doesn't have a useful .toString()
        sb.append(", resultMapId='").append(resultMapId).append('\'');
        sb.append(", jdbcTypeName='").append(jdbcTypeName).append('\'');
        sb.append(", expression='").append(expression).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
