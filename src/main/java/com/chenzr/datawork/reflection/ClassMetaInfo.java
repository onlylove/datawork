package com.chenzr.datawork.reflection;

import java.util.ArrayList;
import java.util.List;

public class ClassMetaInfo {

    private String namespace;
    private String name;
    private List<Field> fields = new ArrayList<>();

    public ClassMetaInfo(String namespace, String name, List<Field> fields) {
        this.namespace = namespace;
        this.name = name;
        this.fields = fields;
    }

    public String getId() {
        if(null == namespace || namespace.length() == 0 ){
            return name;
        }
        return namespace + "." + name;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getName() {
        return name;
    }

    public List<Field> getFields() {
        return fields;
    }

    public static class Field {
        private String name;
        private String type;
        private FieldType fieldType;

        public Field(String name, String type,FieldType fieldType) {
            this.name = name;
            this.type = type;
            this.fieldType = fieldType;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public FieldType getFieldType() {
            return fieldType;
        }

    }

    public enum FieldType {
        BASE, ASSOCIATION, COLLECTION
    }
}