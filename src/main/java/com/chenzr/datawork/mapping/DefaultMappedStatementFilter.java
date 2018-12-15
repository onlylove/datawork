package com.chenzr.datawork.mapping;

import com.chenzr.datawork.reflection.ClassMetaInfo;
import com.chenzr.datawork.reflection.factory.ClassFactory;
import com.chenzr.datawork.reflection.factory.DefaultClassFactory;
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
import java.util.*;

public class DefaultMappedStatementFilter implements MappedStatementFilter {

    private DataSource dataSource;
    private TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry();
    private ClassFactory classFactory = new DefaultClassFactory();

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
            doResultMap(dataSource.getConnection(), ms, ms.getId());
            System.out.println("ok");
        } catch (Exception e) {
            e.printStackTrace();
        }
        chain.doFilter(ms, chain);
    }

    private ResultMap doResultMap(Connection connection, MappedStatement ms, String id) {
        try {
            PreparedStatement pst2 = connection.prepareStatement("SELECT id,class_name,table_name,name,remarks FROM resultmap WHERE id = ?");
            pst2.setString(1, id);
            ResultSet rs2 = pst2.executeQuery();
            String className = null;
            String table = null;
            Class<?> strClass = null;
            if (rs2.next()) {
                className = rs2.getString("class_name");
                table = rs2.getString("table_name");
            }
            rs2.close();
            pst2.close();

            ResultMap map = null;
            Set<String> mappedColumns = new HashSet<>();
            List<ResultMapping> propertyResultMappings = new ArrayList<>();
            List<ResultMapping> idResultMappings = new ArrayList<>();
            List<ResultMapping> resultMappings = new ArrayList<>();
            PreparedStatement pst = connection.prepareStatement("SELECT resultMap_Id,id,property,column_field,jdbc_type,nested_ResultMap_Id,is_Id,foreign_column,join_table,join_column,relation FROM resultmapping WHERE resultMap_Id= ?");
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            String rid = "";
            List<ClassMetaInfo.Field> fieldList = new ArrayList<>();
            while (rs.next()) {
                rid = id;
                ResultFlag flag = null;
                String property = rs.getString("property");
                String column = rs.getString("column_field");
                String foreignColumn = rs.getString("foreign_column");
                String joinTable = rs.getString("join_table");
                String joinColumn = rs.getString("join_column");

                String type = rs.getString("jdbc_type");
                JdbcType jdbcType = JdbcType.forName(type);
                TypeHandler typeHandler = typeHandlerRegistry.getTypeHandler(jdbcType);
                Class javaType = null;
                String typeName = "";
                String prefix = null;
                int relation = rs.getInt("relation");
                String nestedResultMapId = null;
                if (relation > 0) {
                    nestedResultMapId = rs.getString("nested_ResultMap_Id");
                    switch (relation) {
                        case 1:
                            prefix = property + "_";
                            flag = ResultFlag.ONE_TO_ONE;
                            ResultMap resultMap1 = doResultMap(connection, ms, nestedResultMapId);
                            fieldList.add(new ClassMetaInfo.Field(property, resultMap1.getType().getName(), ClassMetaInfo.FieldType.ASSOCIATION));
                            break;
                        case 2:
                            prefix = property + "_";
                            flag = ResultFlag.ONE_TO_MANY;
                            ResultMap resultMap2 = doResultMap(connection, ms, nestedResultMapId);
                            fieldList.add(new ClassMetaInfo.Field(property, resultMap2.getType().getName(), ClassMetaInfo.FieldType.COLLECTION));
                            break;
                        case 3:
                            prefix = property + "_";
                            flag = ResultFlag.MANY_TO_MANY;
                            ResultMap resultMap3 = doResultMap(connection, ms, nestedResultMapId);
                            fieldList.add(new ClassMetaInfo.Field(property, resultMap3.getType().getName(), ClassMetaInfo.FieldType.COLLECTION));
                            break;
                        default:
                            break;
                    }
                }

                if (typeHandler instanceof BaseTypeHandler) {
                    Type rawType = ((BaseTypeHandler) typeHandler).getRawType();
                    if (null != rawType) {
                        typeName = rawType.getTypeName();
                        javaType = Class.forName(typeName);
                    }
                    fieldList.add(new ClassMetaInfo.Field(property, typeName, ClassMetaInfo.FieldType.BASE));
                }
                int isId = rs.getInt("is_Id");
                if (isId == 1) {
                    flag = ResultFlag.ID;
                }
                ResultMapping mapping = new ResultMapping(property, column, javaType, jdbcType, typeHandler, prefix, flag, nestedResultMapId, foreignColumn, joinTable, joinColumn);
                resultMappings.add(mapping);
                propertyResultMappings.add(mapping);
                if (isId == 1) {
                    idResultMappings.add(mapping);
                }
                if (null != column && relation == 0) {
                    mappedColumns.add(column);
                }
            }
            rs.close();
            pst.close();

            try {
                ClassMetaInfo classMetaInfo = new ClassMetaInfo("", className, fieldList);
                strClass = classFactory.create(classMetaInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            map = new ResultMap(rid, table, strClass, resultMappings, idResultMappings, propertyResultMappings, mappedColumns, true);

            ms.addResultMap(map);

            return map;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
