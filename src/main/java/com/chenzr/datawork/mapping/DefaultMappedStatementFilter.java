package com.chenzr.datawork.mapping;

import com.chenzr.datawork.type.BaseTypeHandler;
import com.chenzr.datawork.type.JdbcType;
import com.chenzr.datawork.type.TypeHandler;
import com.chenzr.datawork.type.TypeHandlerRegistry;

import javax.sql.DataSource;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DefaultMappedStatementFilter implements MappedStatementFilter {

    private DataSource dataSource;
    private TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry();

    public DefaultMappedStatementFilter(DataSource _dataSource) {
        this.dataSource = _dataSource;
    }

    @Override
    public String getName() {
        return DefaultMappedStatementFilter.class.getSimpleName();
    }

    @Override
    public void doFilter(MappedStatement ms, MappedStatementFilterChain chain) {
        try {
            doResultMap(dataSource.getConnection(),ms, ms.getId(),null);
            System.out.println("ok");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        chain.doFilter(ms, chain);
    }

    private ResultMap doResultMap(Connection connection, MappedStatement ms, String id, String columnPrefix) {
        try {
            ResultMap map = null;
            List<ResultMapping> nestedResultMapIds = new ArrayList<>();
            Set<String> mappedColumns = new HashSet<>();
            List<ResultMapping> propertyResultMappings = new ArrayList<>();
            List<ResultMapping> idResultMappings = new ArrayList<>();
            List<ResultMapping> resultMappings = new ArrayList<>();
            PreparedStatement pst = connection.prepareStatement("SELECT resultMap_Id,id,property,column_field,jdbc_type,nested_ResultMap_Id,is_Id,foreign_column,join_table,join_column,relation FROM resultmapping WHERE resultMap_Id= ?");
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            String rid = "";
            while (rs.next()) {
                rid = id;
                ResultFlag flag = null;
                String property = rs.getString("property");
                String column = rs.getString("column_field");
                String foreignColumn = rs.getString("foreign_column");
                String joinTable = rs.getString("join_table");
                String joinColumn = rs.getString("join_column");

                int relation = rs.getInt("relation");
                String nestedResultMapId = null;
                if (relation > 0) {
                    nestedResultMapId = rs.getString("nested_ResultMap_Id");
                    switch (relation) {
                        case 1:
                            flag = ResultFlag.ONE_TO_ONE;
                            break;
                        case 2:
                            flag = ResultFlag.ONE_TO_MANY;
                            break;
                        case 3:
                            flag = ResultFlag.MANY_TO_MANY;
                            break;
                        default:
                            break;
                    }
                }

                String type = rs.getString("jdbc_type");
                JdbcType jdbcType = JdbcType.forName(type);
                TypeHandler typeHandler = typeHandlerRegistry.getTypeHandler(jdbcType);
                Type javaType = null;
                if (typeHandler instanceof BaseTypeHandler) {
                    javaType = ((BaseTypeHandler) typeHandler).getRawType();
                }
                int isId = rs.getInt("is_Id");
                if (isId == 1) {
                    flag = ResultFlag.ID;
                }
                ResultMapping mapping = new ResultMapping(property, column, null, jdbcType, typeHandler, columnPrefix, flag, nestedResultMapId, foreignColumn, joinTable, joinColumn);
                resultMappings.add(mapping);
                propertyResultMappings.add(mapping);
                if (isId == 1) {
                    idResultMappings.add(mapping);
                }
                if (null != column && relation == 0) {
                    mappedColumns.add(column);
                }
                if (null != flag && (flag == ResultFlag.ONE_TO_ONE || flag == ResultFlag.ONE_TO_MANY || flag == ResultFlag.MANY_TO_MANY)) {
                    nestedResultMapIds.add(mapping);
                }
            }
            rs.close();
            pst.close();
            pst = connection.prepareStatement("SELECT id,class_name,table_name,name,remarks FROM resultmap WHERE id = ?");
            pst.setString(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                String string = rs.getString("class_name");
                String table = rs.getString("table_name");
                Class<?> strClass = null;
                try {
                    strClass = Class.forName(string);
                    map = new ResultMap(rid, table, strClass, resultMappings, idResultMappings, propertyResultMappings, mappedColumns, true);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            rs.close();
            pst.close();
            ms.addResultMap(map);
            for (ResultMapping mapping : nestedResultMapIds) {
                doResultMap(connection, ms, mapping.getNestedResultMapId(), mapping.getProperty()+"_");
            }
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
