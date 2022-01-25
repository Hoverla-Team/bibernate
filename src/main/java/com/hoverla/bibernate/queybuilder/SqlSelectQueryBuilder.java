package com.hoverla.bibernate.queybuilder;

import java.util.Map;

/**
 * {@link SqlSelectQueryBuilder} interface used to create select query with where clause and limit, for
 * future execution. Extends {@link SqlQueryBuilder} marker interface.
 * @author Maryna Melnychuk
 * @since 01.01.2022
 **/
public interface SqlSelectQueryBuilder extends SqlQueryBuilder {

    /**
     * Build SQL select query by specified entity class, criteria and limit.
     *
     * @param entityClass - type of entity to find
     * @param criteria - criteria used to form where clause
     * @param limit - number of records to query
     * @return SQL query as {@link String}
     **/
    String buildSqlSelectQuery(Class<?> entityClass, Map<String, Object> criteria, int limit);

    /**
     * Build SQL select query by specified entity class and criteria.
     *
     * @param entityClass - type of entity to find
     * @param criteria - criteria used to form where clause
     * @return SQL query as {@link String}
     **/
    String buildSqlSelectQuery(Class<?> entityClass, Map<String, Object> criteria);

    /**
     * Build SQL select query by specified entity class and limit.
     *
     * @param entityClass - type of entity to find
     * @param limit - number of records to query
     * @return SQL query as {@link String}
     **/
    String buildSqlSelectQuery(Class<?> entityClass, int limit);

    /**
     * Build simple SQL select query.
     *
     * @param entityClass - type of entity to find
     * @return SQL query as {@link String}
     **/
    String buildSqlSelectQuery(Class<?> entityClass);

}
