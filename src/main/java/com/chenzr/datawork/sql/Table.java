package com.chenzr.datawork.sql;

import java.util.ArrayList;
import java.util.List;

public class Table {

    private String prefix;
    private String alias;
    private String name;
    private Column primaryKey;
    private List<Column> columns = new ArrayList<>();

    private Column joinColumn;
    private Join join;
    private Table joinTable;
    private Column joinTableColumn;


    public Table(String name, String alias, String prefix,Join join,Table joinTable,Column joinTableColumn,Column joinColumn){
        this.alias = alias;
        this.name = name;
        this.prefix = prefix;
        this.join = join;
        this.joinTable = joinTable;
        this.joinTableColumn = joinTableColumn;
        this.joinColumn = joinColumn;
    }

    public String getAlias() {
        return alias;
    }

    public void addColumn(Column column){
        columns.add(column);
        if(column.isPrimaryKey()){
            primaryKey = column;
        }
    }

    public String getPrefix() {
        return prefix;
    }

    public Join getJoin() {
        return join;
    }

    public Table getJoinTable() {
        return joinTable;
    }

    public Column getPrimaryKey() {
        return primaryKey;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public String getName() {
        return name;
    }

    public Column getJoinColumn() {
        return joinColumn;
    }

    public Column getJoinTableColumn() {
        return joinTableColumn;
    }
}
