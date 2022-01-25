package com.hoverla.bibernate.queryBuilder.impl;

import com.hoverla.bibernate.annotation.Table;
import com.hoverla.bibernate.util.ResultSetParser;
import com.hoverla.bibernate.util.impl.ResultSetParserImpl;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
public class SqlSaveQueryBuilder {

    private final ResultSetParser resultSetParser;

    public SqlSaveQueryBuilder() {
        this.resultSetParser = new ResultSetParserImpl();
    }

    private static final String INSERT_INTO_TABLE = "INSERT INTO %s (%s) VALUES (%s)";

    public String buildSaveQuery(Object objToSave) {
        String tableName = resolveTableName(objToSave.getClass());
        List<String> fieldNames = resolveFieldNames(objToSave);
        return String.format(INSERT_INTO_TABLE,
            tableName,
            String.join(", ", fieldNames),
            fieldNames.stream()
                .map(f -> "?")
                .collect(Collectors.joining(", ")));
    }

    private List<String> resolveFieldNames(Object objToSave) {
        Field[] declaredFields = objToSave.getClass().getDeclaredFields();
        List<String> fieldNames = new ArrayList<>();
        for (int i = 1; i < declaredFields.length; i++) {
            declaredFields[i].setAccessible(true);
            fieldNames.add(resultSetParser.getEntityColumnName(declaredFields[i]));
        }
        log.debug("Getting declared fields from object: {}", String.join(", ", fieldNames));
        return fieldNames;
    }

    private String resolveTableName(Class<?> entityType) {
        log.trace("Resolving table name for entity: {}", entityType);
        Table tableAnnotation = entityType.getDeclaredAnnotation(Table.class);
        if (tableAnnotation != null) {
            String tableName = tableAnnotation.value();
            log.trace("Table name is specified explicitly: {}", tableName);
            return tableName;
        }
        log.trace("Table is not explicitly specified,falling back to call name {}", entityType);
        return entityType.getSimpleName().toLowerCase();
    }
}
