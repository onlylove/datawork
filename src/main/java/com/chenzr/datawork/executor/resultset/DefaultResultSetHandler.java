package com.chenzr.datawork.executor.resultset;


import com.chenzr.datawork.cache.CacheKey;
import com.chenzr.datawork.executor.ExecutorException;
import com.chenzr.datawork.executor.result.DefaultResultHandler;
import com.chenzr.datawork.executor.result.ResultHandler;
import com.chenzr.datawork.mapping.ResultMap;
import com.chenzr.datawork.mapping.ResultMapping;
import com.chenzr.datawork.reflection.DefaultReflectorFactory;
import com.chenzr.datawork.reflection.MetaClass;
import com.chenzr.datawork.reflection.MetaObject;
import com.chenzr.datawork.reflection.ReflectorFactory;
import com.chenzr.datawork.reflection.factory.DefaultObjectFactory;
import com.chenzr.datawork.reflection.factory.ObjectFactory;
import com.chenzr.datawork.reflection.wrapper.DefaultObjectWrapperFactory;
import com.chenzr.datawork.reflection.wrapper.ObjectWrapperFactory;
import com.chenzr.datawork.type.TypeHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class DefaultResultSetHandler implements ResultSetHandler {

    protected ObjectWrapperFactory objectWrapperFactory = new DefaultObjectWrapperFactory();
    private ReflectorFactory reflectorFactory = new DefaultReflectorFactory();
    private ObjectFactory objectFactory = new DefaultObjectFactory();
    private ResultHandler resultHandler = null;

    private HashMap<String, ResultMap> resultMaps = new HashMap<>();

    private HashMap<CacheKey, Object> nestedResultObjects = new HashMap<CacheKey, Object>();
    private HashMap<CacheKey, Object> simpleResultObjects = new HashMap<CacheKey, Object>();

    public MetaObject newMetaObject(Object object) {
        return MetaObject.forObject(object, objectFactory, objectWrapperFactory, reflectorFactory);
    }

    private ResultMap createResultMap() {

//        //========Author=========//
//        List<ResultMapping> AuthorResultMappings = new ArrayList<>();
//
//        ResultMapping AuthorID = new ResultMapping("id", "id", Integer.class, JdbcType.INTEGER, new IntegerTypeHandler(), null, null, null);
//        AuthorResultMappings.add(AuthorID);
//        ResultMapping AuthorName = new ResultMapping("username", "username", String.class, JdbcType.VARCHAR, new StringTypeHandler(), null, null, null);
//        AuthorResultMappings.add(AuthorName);
//
//        List<ResultMapping> AuthorIDResultMappings = new ArrayList<>();
//        AuthorIDResultMappings.add(AuthorID);
//
//        List<ResultMapping> AuthorPropertyResultMappings = new ArrayList<>();
//        AuthorPropertyResultMappings.add(AuthorID);
//        AuthorPropertyResultMappings.add(AuthorName);
//
//        Set<String> AuthormappedColumns = new HashSet<>();
//        AuthormappedColumns.add("id");
//        AuthormappedColumns.add("username");
//
//        ResultMap AuthorMap = new ResultMap("_Author", Author.class, AuthorResultMappings, AuthorIDResultMappings, AuthorPropertyResultMappings, AuthormappedColumns, true);
//        resultMaps.put("_Author", AuthorMap);
//        //========Author=========//
//
//        //========Comment=========//
//        List<ResultMapping> commentResultMappings = new ArrayList<>();
//
//        ResultMapping commentresultID = new ResultMapping("id", "id", Integer.class, JdbcType.INTEGER, new IntegerTypeHandler(), null, null, null);
//        commentResultMappings.add(commentresultID);
//        ResultMapping commenresultName = new ResultMapping("name", "name", String.class, JdbcType.VARCHAR, new StringTypeHandler(), null, null, null);
//        commentResultMappings.add(commenresultName);
//        ResultMapping commentresultNamecomment = new ResultMapping("comment", "text", String.class, JdbcType.VARCHAR, new StringTypeHandler(), null, null, null);
//        commentResultMappings.add(commentresultNamecomment);
//
//        List<ResultMapping> commentIDResultMappings = new ArrayList<>();
//        commentIDResultMappings.add(commentresultID);
//
//        List<ResultMapping> commentPropertyResultMappings = new ArrayList<>();
//        commentPropertyResultMappings.add(commentresultID);
//        commentPropertyResultMappings.add(commenresultName);
//        commentPropertyResultMappings.add(commentresultNamecomment);
//
//        Set<String> commentmappedColumns = new HashSet<>();
//        commentmappedColumns.add("id");
//        commentmappedColumns.add("name");
//        commentmappedColumns.add("comment");
//
//        ResultMap commentMap = new ResultMap("_Comment", Comment.class, commentResultMappings, commentIDResultMappings, commentPropertyResultMappings, commentmappedColumns, true);
//        resultMaps.put("_Comment", commentMap);
//        //========Comment=========//
//
//        //========POST=========//
//        List<ResultMapping> postResultMappings = new ArrayList<>();
//
//        ResultMapping postresultID = new ResultMapping("id", "id", Integer.class, JdbcType.INTEGER, new IntegerTypeHandler(), null, null, null);
//        postResultMappings.add(postresultID);
//        ResultMapping postresultbody = new ResultMapping("body", "body", String.class, JdbcType.VARCHAR, new StringTypeHandler(), null, null, null);
//        postResultMappings.add(postresultbody);
//
//        ResultMapping resultcomment = new ResultMapping("comments", null, Collection.class, null, new ArrayTypeHandler(), "comment_", null, "_Comment");
//        postResultMappings.add(resultcomment);
//
//        List<ResultMapping> postIdResultMappings = new ArrayList<>();
//        postIdResultMappings.add(postresultID);
//
//        List<ResultMapping> postPropertyResultMappings = new ArrayList<>();
//        postPropertyResultMappings.add(postresultID);
//        postPropertyResultMappings.add(postresultbody);
//        postPropertyResultMappings.add(resultcomment);
//
//        Set<String> postmappedColumns = new HashSet<>();
//        postmappedColumns.add("id");
//        postmappedColumns.add("body");
//
//        ResultMap postMap = new ResultMap("_Post", Post.class, postResultMappings, postIdResultMappings, postPropertyResultMappings, postmappedColumns, true);
//        resultMaps.put("_Post", postMap);
//
//        //========POST=========//
//
//
//        //========Blog=========//
//        List<ResultMapping> resultMappings = new ArrayList<>();
//
//        ResultMapping resultID = new ResultMapping("id", "id", Integer.class, JdbcType.INTEGER, new IntegerTypeHandler(), null, null, null);
//        resultMappings.add(resultID);
//        ResultMapping resultTitle = new ResultMapping("title", "title", Integer.class, JdbcType.VARCHAR, new StringTypeHandler(), null, null, null);
//        resultMappings.add(resultTitle);
//
//        ResultMapping resultPost = new ResultMapping("posts", null, Collection.class, null, new ArrayTypeHandler(), "post_", null, "_Post");
//        resultMappings.add(resultPost);
//
//        ResultMapping resultAuthor = new ResultMapping("author", null, Author.class, null, null, "author_", null, "_Author");
//        resultMappings.add(resultAuthor);
//
//        List<ResultMapping> idResultMappings = new ArrayList<>();
//        idResultMappings.add(resultID);
//
//        List<ResultMapping> propertyResultMappings = new ArrayList<>();
//        propertyResultMappings.add(resultID);
//        propertyResultMappings.add(resultTitle);
//        propertyResultMappings.add(resultPost);
//        propertyResultMappings.add(resultAuthor);
//
//        Set<String> mappedColumns = new HashSet<>();
//        mappedColumns.add("id");
//        mappedColumns.add("title");
//
//        ResultMap map = new ResultMap("_Blog", Blog.class, resultMappings, idResultMappings, propertyResultMappings, mappedColumns, true);
//        //========Blog=========//
//        resultMaps.put("_Blog", postMap);

        return null;
    }


    @Override
    public List<Object> handleResultSets(Statement stmt) throws SQLException {
        final List<Object> multipleResults = new ArrayList<Object>();
        try{
            ResultSetWrapper rsw = getFirstResultSet(stmt);
            ResultMap rm = createResultMap();
            handleResultSet(rsw, rm, multipleResults, null);
            return collapseSingleResultList(multipleResults);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cleanUpAfterHandlingResultSet();
        }
        return collapseSingleResultList(multipleResults);
    }

    private void handleResultSet(ResultSetWrapper rsw, ResultMap resultMap, List<Object> multipleResults, ResultMapping parentMapping) throws SQLException {
        try {
            if (parentMapping != null) {
                handleRowValues(rsw, resultMap, null, parentMapping);
            } else {
                if (resultHandler == null) {
                    DefaultResultHandler defaultResultHandler = new DefaultResultHandler(objectFactory);
                    handleRowValues(rsw, resultMap, defaultResultHandler, null);
                    multipleResults.add(defaultResultHandler.getResultList());
                } else {
                    handleRowValues(rsw, resultMap, resultHandler, null);
                }
            }
        } finally {
            closeResultSet(rsw.getResultSet());
        }
    }

    private void handleRowValues(ResultSetWrapper rsw, ResultMap resultMap, ResultHandler resultHandler, ResultMapping parentMapping) throws SQLException {
        if (resultMap.hasNestedResultMaps()) {
            handleRowValuesForNestedResultMap(rsw, resultMap, resultHandler, parentMapping);
        } else {
            handleRowValuesForSimpleResultMap(rsw, resultMap, resultHandler, parentMapping);
        }
    }

    private void handleRowValuesForSimpleResultMap(ResultSetWrapper rsw, ResultMap resultMap, ResultHandler<?> resultHandler, ResultMapping parentMapping)
            throws SQLException {
        DefaultResultContext<Object> resultContext = new DefaultResultContext<Object>();
        while (rsw.getResultSet().next()) {
            final CacheKey rowKey = createRowKey(resultMap, rsw, null);
            Object partialObject = simpleResultObjects.get(rowKey);
            Object rowValue = getRowValue(rsw, resultMap, rowKey);
            if (partialObject == null) {
                storeObject(resultHandler, resultContext, rowValue, parentMapping, rsw.getResultSet());
            }
        }
    }

    //
    // GET VALUE FROM ROW FOR SIMPLE RESULT MAP
    //
    private Object getRowValue(ResultSetWrapper rsw, ResultMap resultMap,CacheKey rowKey) throws SQLException {
        Object resultObject = createResultObject(rsw, resultMap, null);
        if (resultObject != null) {
            final MetaObject metaObject = newMetaObject(resultObject);
            boolean foundValues = false;
            foundValues = applyPropertyMappings(rsw, resultMap, metaObject, null) || foundValues;
            resultObject = foundValues ? resultObject : null;
            if (rowKey != CacheKey.NULL_CACHE_KEY && null != resultObject) {
                simpleResultObjects.put(rowKey, resultObject);
            }
            return resultObject;
        }
        return resultObject;
    }

    private void handleRowValuesForNestedResultMap(ResultSetWrapper rsw, ResultMap resultMap, ResultHandler resultHandler, ResultMapping parentMapping) throws SQLException {
        final DefaultResultContext<Object> resultContext = new DefaultResultContext<Object>();
        Object rowValue = null;
        while (rsw.getResultSet().next()) {
            final CacheKey rowKey = createRowKey(resultMap, rsw, null);
            Object partialObject = nestedResultObjects.get(rowKey);
            rowValue = getRowValue(rsw, resultMap, rowKey, rowKey, null, partialObject);
            if (partialObject == null) {
                storeObject(resultHandler, resultContext, rowValue, parentMapping, rsw.getResultSet());
            }
        }
    }

    private Object getRowValue(ResultSetWrapper rsw, ResultMap resultMap, CacheKey combinedKey, CacheKey absoluteKey, String columnPrefix, Object partialObject) throws SQLException {
        final String resultMapId = resultMap.getId();
        Object resultObject = partialObject;
        boolean foundValues = false;
        if (resultObject != null) {
            final MetaObject metaObject = newMetaObject(resultObject);
            applyNestedResultMappings(rsw, resultMap, metaObject, columnPrefix, combinedKey, false);
        } else {
            resultObject = createResultObject(rsw, resultMap, columnPrefix);
            final MetaObject metaObject = newMetaObject(resultObject);
            if (resultObject != null) {
                foundValues = applyPropertyMappings(rsw, resultMap, metaObject, columnPrefix) || foundValues;
                foundValues = applyNestedResultMappings(rsw, resultMap, metaObject, columnPrefix, combinedKey, true) || foundValues;
                resultObject = foundValues ? resultObject : null;
            }
            if (combinedKey != CacheKey.NULL_CACHE_KEY) {
                nestedResultObjects.put(combinedKey, resultObject);
            }
        }
        return resultObject;
    }

    //
    // PROPERTY MAPPINGS
    //
    private boolean applyPropertyMappings(ResultSetWrapper rsw, ResultMap resultMap, MetaObject metaObject,String columnPrefix)
            throws SQLException {
        boolean foundValues = false;
        final List<ResultMapping> propertyMappings = resultMap.getPropertyResultMappings();
        for (ResultMapping propertyMapping : propertyMappings) {
            String column = prependPrefix(propertyMapping.getColumn(), columnPrefix);
            if (propertyMapping.getNestedResultMapId() != null) {
                column = null;
            }
            if (column != null) {
                Object value = getPropertyMappingValue(rsw.getResultSet(), propertyMapping, columnPrefix);
                final String property = propertyMapping.getProperty();
                if (value != null && property != null) {
                    metaObject.setValue(property, value);
                    foundValues = true;
                }
            }
        }
        return foundValues;
    }

    private Object getPropertyMappingValue(ResultSet rs, ResultMapping propertyMapping, String columnPrefix)
            throws SQLException {
            final TypeHandler<?> typeHandler = propertyMapping.getTypeHandler();
            final String column = prependPrefix(propertyMapping.getColumn(), columnPrefix);
            return typeHandler.getResult(rs, column);
    }

    //
    // NESTED RESULT MAP (Join MAPPING)
    //
    private boolean applyNestedResultMappings(ResultSetWrapper rsw, ResultMap resultMap, MetaObject metaObject, String parentPrefix, CacheKey parentRowKey, boolean newObject) {
        boolean foundValues = false;
        for (ResultMapping resultMapping : resultMap.getPropertyResultMappings()) {
            final String nestedResultMapId = resultMapping.getNestedResultMapId();
            if (nestedResultMapId != null ) {
                try {
                    final String columnPrefix = getColumnPrefix(parentPrefix, resultMapping);
                    final ResultMap nestedResultMap = getNestedResultMap(rsw.getResultSet(), nestedResultMapId, columnPrefix);
                    CacheKey rowKey = createRowKey(nestedResultMap, rsw, columnPrefix);
                    final CacheKey combinedKey = combineKeys(rowKey, parentRowKey);
                    Object rowValue = nestedResultObjects.get(combinedKey);
                    boolean knownValue = (rowValue != null);
                    rowValue = getRowValue(rsw, nestedResultMap, combinedKey, rowKey, columnPrefix, rowValue);
                    if (rowValue != null && !knownValue) {
                        linkObjects(metaObject, resultMapping, rowValue);
                        foundValues = true;
                    }
                } catch (SQLException e) {
                    throw new ExecutorException("Error getting nested result map values for '" + resultMapping.getProperty() + "'.  Cause: " + e, e);
                }
            }
        }
        return foundValues;
    }

    private ResultMap getNestedResultMap(ResultSet rs, String nestedResultMapId, String columnPrefix) throws SQLException {
        ResultMap nestedResultMap = resultMaps.get(nestedResultMapId);
        return nestedResultMap;
    }

    private void linkObjects(MetaObject metaObject, ResultMapping resultMapping, Object rowValue) {
        final Object collectionProperty = instantiateCollectionPropertyIfAppropriate(resultMapping, metaObject);
        if (collectionProperty != null) {
            final MetaObject targetMetaObject = newMetaObject(collectionProperty);
            targetMetaObject.add(rowValue);
        } else {
            metaObject.setValue(resultMapping.getProperty(), rowValue);
        }
    }

    private Object instantiateCollectionPropertyIfAppropriate(ResultMapping resultMapping, MetaObject metaObject) {
        final String propertyName = resultMapping.getProperty();
        Object propertyValue = metaObject.getValue(propertyName);
        if (propertyValue == null) {
            Class<?> type = resultMapping.getJavaType();
            if (type == null) {
                type = metaObject.getSetterType(propertyName);
            }
            try {
                if (objectFactory.isCollection(type)) {
                    propertyValue = objectFactory.create(type);
                    metaObject.setValue(propertyName, propertyValue);
                    return propertyValue;
                }
            } catch (Exception e) {
                throw new ExecutorException("Error instantiating collection property for result '" + resultMapping.getProperty() + "'.  Cause: " + e, e);
            }
        } else if (objectFactory.isCollection(propertyValue.getClass())) {
            return propertyValue;
        }
        return null;
    }

    //
    // INSTANTIATION & CONSTRUCTOR MAPPING
    //
    private Object createResultObject(ResultSetWrapper rsw, ResultMap resultMap, String columnPrefix)
            throws SQLException {
        final Class<?> resultType = resultMap.getType();
        final MetaClass metaType = MetaClass.forClass(resultType, reflectorFactory);
        return objectFactory.create(resultType);
    }

    private String getColumnPrefix(String parentPrefix, ResultMapping resultMapping) {
        final StringBuilder columnPrefixBuilder = new StringBuilder();
        if (parentPrefix != null) {
            columnPrefixBuilder.append(parentPrefix);
        }
        if (resultMapping.getColumnPrefix() != null) {
            columnPrefixBuilder.append(resultMapping.getColumnPrefix());
        }
        return columnPrefixBuilder.length() == 0 ? null : columnPrefixBuilder.toString().toUpperCase(Locale.ENGLISH);
    }

    private String prependPrefix(String columnName, String prefix) {
        if (columnName == null || columnName.length() == 0 || prefix == null || prefix.length() == 0) {
            return columnName;
        }
        return prefix + columnName;
    }

    private CacheKey createRowKey(ResultMap resultMap, ResultSetWrapper rsw, String columnPrefix) throws SQLException {
        final CacheKey cacheKey = new CacheKey();
        cacheKey.update(resultMap.getId());
        List<ResultMapping> resultMappings = getResultMappingsForRowKey(resultMap);
        createRowKeyForMappedProperties(resultMap, rsw, cacheKey, resultMappings, columnPrefix);
        return cacheKey;
    }

    private void createRowKeyForMappedProperties(ResultMap resultMap, ResultSetWrapper rsw, CacheKey cacheKey, List<ResultMapping> resultMappings, String columnPrefix) throws SQLException {
        for (ResultMapping resultMapping : resultMappings) {
            if (resultMapping.getNestedResultMapId() == null) {
                final String column = prependPrefix(resultMapping.getColumn(), columnPrefix);
                final TypeHandler th = resultMapping.getTypeHandler();
                final Object value = th.getResult(rsw.getResultSet(), column);
                if (value != null) {
                    cacheKey.update(column);
                    cacheKey.update(value);
                }
            }
        }
    }

    private CacheKey combineKeys(CacheKey rowKey, CacheKey parentRowKey) {
        if (rowKey.getUpdateCount() > 1 && parentRowKey.getUpdateCount() > 1) {
            CacheKey combinedKey;
            try {
                combinedKey = rowKey.clone();
            } catch (CloneNotSupportedException e) {
                throw new ExecutorException("Error cloning cache key.  Cause: " + e, e);
            }
            combinedKey.update(parentRowKey);
            return combinedKey;
        }
        return CacheKey.NULL_CACHE_KEY;
    }

    private List<ResultMapping> getResultMappingsForRowKey(ResultMap resultMap) {
        List<ResultMapping> resultMappings = resultMap.getIdResultMappings();
        if (resultMappings.size() == 0) {
            resultMappings = resultMap.getPropertyResultMappings();
        }
        return resultMappings;
    }

    private void storeObject(ResultHandler resultHandler, DefaultResultContext<Object> resultContext, Object rowValue,ResultMapping parentMapping, ResultSet rs) throws SQLException {
        if (parentMapping != null) {
           // linkToParents(rs, parentMapping, rowValue);
        } else {
            callResultHandler(resultHandler, resultContext, rowValue);
        }
    }

    private void callResultHandler(ResultHandler resultHandler, DefaultResultContext<Object> resultContext, Object rowValue) {
        resultContext.nextResultObject(rowValue);
        ((ResultHandler)resultHandler).handleResult(resultContext);
    }


    private ResultSetWrapper getFirstResultSet(Statement stmt) throws SQLException {
        ResultSet rs = stmt.getResultSet();
        while (rs == null) {
            // move forward to get the first resultset in case the driver
            // doesn't return the resultset as the first result (HSQLDB 2.1)
            if (stmt.getMoreResults()) {
                rs = stmt.getResultSet();
            } else {
                if (stmt.getUpdateCount() == -1) {
                    // no more results. Must be no resultset
                    break;
                }
            }
        }
        return rs != null ? new ResultSetWrapper(rs) : null;
    }

    private ResultSetWrapper getNextResultSet(Statement stmt) throws SQLException {
        // Making this method tolerant of bad JDBC drivers
        try {
            if (stmt.getConnection().getMetaData().supportsMultipleResultSets()) {
                // Crazy Standard JDBC way of determining if there are more results
                if (!((!stmt.getMoreResults()) && (stmt.getUpdateCount() == -1))) {
                    ResultSet rs = stmt.getResultSet();
                    return rs != null ? new ResultSetWrapper(rs) : null;
                }
            }
        } catch (Exception e) {
            // Intentionally ignored.
        }
        return null;
    }

    private void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            // ignore
        }
    }

    private List<Object> collapseSingleResultList(List<Object> multipleResults) {
        return multipleResults.size() == 1 ? (List<Object>) multipleResults.get(0) : multipleResults;
    }

    private void cleanUpAfterHandlingResultSet() {
        nestedResultObjects.clear();
        simpleResultObjects.clear();
    }

}
