package com.chenzr.datawork.mapping;


import com.chenzr.datawork.executor.keygen.KeyGenerator;
import com.chenzr.datawork.sql.Column;
import com.chenzr.datawork.sql.Join;
import com.chenzr.datawork.sql.Select;
import com.chenzr.datawork.sql.Table;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class MappedStatement {

    private int alias = 0;
    private String id;
    private ParameterMap parameterMap = new ParameterMap();
    private Map<String, ResultMap> resultMapsMap = new HashMap<>();
    private CommandType sqlCommandType = CommandType.UNKNOWN;
    private KeyGenerator keyGenerator;
    private boolean hasNestedResultMaps;

    public MappedStatement(String _id) {
        this.id = _id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ParameterMap getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(ParameterMap parameterMap) {
        this.parameterMap = parameterMap;
    }

    public CommandType getSqlCommandType() {
        return sqlCommandType;
    }

    public void setSqlCommandType(CommandType sqlCommandType) {
        this.sqlCommandType = sqlCommandType;
    }

    public KeyGenerator getKeyGenerator() {
        return keyGenerator;
    }

    public void setKeyGenerator(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    public boolean isHasNestedResultMaps() {
        return hasNestedResultMaps;
    }

    public void setHasNestedResultMaps(boolean hasNestedResultMaps) {
        this.hasNestedResultMaps = hasNestedResultMaps;
    }


    private ResultMap getResultMap(String id) throws SQLException {
        return resultMapsMap.get(id);
    }

    public void addResultMap(ResultMap map) {
        resultMapsMap.put(map.getId(), map);
    }

    public String getSql() {
        Select select = new Select();
        doSql(select,  id, null, null, null,null,null);
        return select.getSql();
    }

    private void doSql(Select select, String id, String prefix, Join join, Table joinTable, Column joinTableColumn,Column joinColumn) {
        ResultMap map = resultMapsMap.get(id);
        if (null != map) {
            alias++;
            Table table = new Table(map.getTable(), "t" + alias, prefix, join, joinTable,joinTableColumn,joinColumn);
            for (ResultMapping mapping : map.getPropertyResultMappings()) {
                Column column = new Column(mapping.getColumn(), ResultFlag.ID == mapping.getFlag());
                if(null == mapping.getFlag() || ResultFlag.ID == mapping.getFlag()){
                    table.addColumn(column);
                }
            }
            select.getTables().add(table);
            for (ResultMapping mapping : map.getPropertyResultMappings()) {
                if (null != mapping.getFlag() && (ResultFlag.ONE_TO_ONE == mapping.getFlag())) {
                    mapping.getForeignColumn();
                    mapping.getJoinColumn();
                    mapping.getJoinTable();
                    mapping.getJoinTable();
                    mapping.getFlag();
                    ResultMap temp = resultMapsMap.get(mapping.getNestedResultMapId());
                    if(null != temp && !temp.getIdResultMappings().isEmpty()){
                        ResultMapping mapping1 = temp.getIdResultMappings().get(0);
                        doSql(select, mapping.getNestedResultMapId(), mapping.getColumnPrefix(), Join.LEFT_OUTER_JOIN, table,new Column(mapping.getForeignColumn(),false),new Column(mapping1.getColumn(),true));
                    }
                }

                if (null != mapping.getFlag() && (ResultFlag.ONE_TO_MANY == mapping.getFlag())) {
                    doSql(select,  mapping.getNestedResultMapId(), mapping.getColumnPrefix(), Join.LEFT_OUTER_JOIN, table,table.getPrimaryKey(),new Column(mapping.getForeignColumn(),false));
                }

                if (null != mapping.getFlag() && (ResultFlag.MANY_TO_MANY == mapping.getFlag())) {

                    // doSql(select, a + 1, mapping.getNestedResultMapId(), mapping.getColumnPrefix(), Join.LEFT_OUTER_JOIN, table,table.getPrimaryKey(),new Column(mapping.getForeignColumn(),false));

                }
            }
        }
    }
}
