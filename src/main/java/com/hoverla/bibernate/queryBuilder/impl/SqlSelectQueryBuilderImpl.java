package com.hoverla.bibernate.queryBuilder.impl;

import com.hoverla.bibernate.annotation.Entity;
import com.hoverla.bibernate.queryBuilder.SqlSelectQueryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * {@link SqlSelectQueryBuilderImpl} is implementation of {@link SqlSelectQueryBuilder} interface.
 * @author Maryna Melnychuk
 * @since 01.01.2022
 **/
@Slf4j
public class SqlSelectQueryBuilderImpl implements SqlSelectQueryBuilder {

    private final String SELECT_FROM_STATEMENT = "SELECT * FROM ";
    private final String LIMIT = " LIMIT ";
    private final String WHERE = " WHERE ";

    @Override
    public String buildSqlSelectQuery(Class<?> entityClass, Map<String, Object> criteria, int limit) {
        String tableName = retrieveTableName(entityClass);
        String whereClause = retrieveWhereClause(criteria);
        String selectQuery = formQuery(tableName, whereClause, limit);
        log.debug("Query to execute: " + selectQuery);
        return selectQuery;
    }

    @Override
    public String buildSqlSelectQuery(Class<?> entityClass, Map<String, Object> criteria) {
        String tableName = retrieveTableName(entityClass);
        String whereClause = retrieveWhereClause(criteria);
        String selectQuery = formQuery(tableName, whereClause);
        log.debug("Query to execute: " + selectQuery);
        return selectQuery;
    }

    @Override
    public String buildSqlSelectQuery(Class<?> entityClass, int limit) {
        String tableName = retrieveTableName(entityClass);
        String selectQuery = formQuery(tableName, limit);
        log.debug("Query to execute: " + selectQuery);
        return selectQuery;
    }

    @Override
    public String buildSqlSelectQuery(Class<?> entityClass) {
        String tableName = retrieveTableName(entityClass);
        String selectQuery = formQuery(tableName);
        log.debug("Query to execute: " + selectQuery);
        return selectQuery;
    }

    private String formQuery(String tableName, String whereClause, int limit) {
        return SELECT_FROM_STATEMENT + tableName + WHERE
                + whereClause
                + (limit > 0 ? LIMIT + limit : "");
    }

    private String formQuery(String tableName, String whereClause) {
        return SELECT_FROM_STATEMENT + tableName + WHERE + whereClause;
    }

    private String formQuery(String tableName) {
        return SELECT_FROM_STATEMENT + tableName;
    }

    private String formQuery(String tableName, int limit) {
        return SELECT_FROM_STATEMENT + tableName
                + (limit > 0 ? LIMIT + limit : "");
    }

    private String retrieveWhereClause(Map<String, Object> criteria) {
        return criteria.keySet()
                .stream().map(field -> field + " = ? ")
                .collect(Collectors.joining(" "));
    }

    private <T> String retrieveTableName(Class<T> entityClass) {
        var tableAnnotation = entityClass.getDeclaredAnnotation(Entity.class);
        return Optional.ofNullable(tableAnnotation)
                .map(Entity::table)
                .orElseGet(() -> entityClass.getSimpleName().toLowerCase());
    }
}
