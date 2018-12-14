package com.chenzr.datawork.sql;

import java.util.ArrayList;
import java.util.List;

public class Select implements SQL {

    private List<Table> tables = new ArrayList<>();
    private List<Column> columns = new ArrayList<>();
    private Where where = new Where();

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public Where getWhere() {
        return where;
    }

    public void setWhere(Where where) {
        this.where = where;
    }

    public String getSql(){
        StringBuilder sb = new StringBuilder();
        StringBuilder columnStr = new StringBuilder();
        StringBuilder fromStr = new StringBuilder();
        boolean first = true;
        for (Table table : tables) {
            for (Column column : table.getColumns()){
                if(!first){
                    columnStr.append(",");
                }
                first = false;
                columnStr.append(table.getAlias());
                columnStr.append(".");
                columnStr.append(column.getName());
                columnStr.append(" AS ");
                if(null != table.getPrefix()){
                    columnStr.append(table.getPrefix());
                }
                columnStr.append(column.getName());
                columnStr.append(" ");
            }
            if(null != table.getJoin()){
                fromStr.append(" ");
                fromStr.append("left outer join");
                fromStr.append(" ");
            }
            fromStr.append(table.getName());
            fromStr.append(" ");
            fromStr.append(table.getAlias());
            fromStr.append(" ");
            if(null != table.getJoin()){
                fromStr.append(" ");
                fromStr.append("ON");
                fromStr.append(" ");
                fromStr.append(table.getAlias());
                fromStr.append(".");
                fromStr.append(table.getJoinColumn().getName());
                fromStr.append("=");
                fromStr.append(table.getJoinTable().getAlias());
                fromStr.append(".");
                fromStr.append(table.getJoinTableColumn().getName());
                fromStr.append(" ");
            }
        }
        return sb.append("select ").append(columnStr).append(" from ").append(fromStr).toString();
    }

    @Override
    public List<String> getParameter() {
        return null;
    }
}
