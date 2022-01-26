package com.hoverla.bibernate.dbOperations.impl;

import com.hoverla.bibernate.annotation.Id;
import com.hoverla.bibernate.exception.BibernateApplicationException;
import com.hoverla.bibernate.exception.NoIdFieldException;
import com.hoverla.bibernate.dbOperations.Finder;
import com.hoverla.bibernate.queryBuilder.SqlSelectQueryBuilder;
import com.hoverla.bibernate.queryBuilder.impl.SqlSelectQueryBuilderImpl;
import com.hoverla.bibernate.util.ResultSetParser;
import com.hoverla.bibernate.util.StatementHandler;
import com.hoverla.bibernate.util.impl.ResultSetParserImpl;
import com.hoverla.bibernate.util.impl.StatementHandlerImpl;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * {@link FinderImpl is class that implements {@link Finder} interface}
 * @author Maryna Melnychuk
 * @since 01.01.2022
 **/
@Slf4j
public class FinderImpl implements Finder {

    private final SqlSelectQueryBuilder SQL_QUERY_BUILDER = new SqlSelectQueryBuilderImpl();
    private final ResultSetParser RESULT_SET_PARSER = new ResultSetParserImpl();
    private final StatementHandler STATEMENT_HANDLER = new StatementHandlerImpl();

    private final Connection connection;

    public FinderImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <T> Optional<T> findById(Class<T> entityClass, Object id) {
        String idField = retrieveIdFieldName(entityClass);
        return find(entityClass, Map.of(idField, id))
                .stream()
                .findFirst();
    }

    @Override
    public <T> List<T> find(Class<T> entityClass) {
        String selectQuery = SQL_QUERY_BUILDER.buildSqlSelectQuery(entityClass);
        return prepareAndExecuteQuery(entityClass, null, selectQuery);
    }

    @Override
    public <T> List<T> find(Class<T> entityClass, Map<String, Object> criteria) {
        String selectQuery = SQL_QUERY_BUILDER.buildSqlSelectQuery(entityClass, criteria);
        return prepareAndExecuteQuery(entityClass, criteria, selectQuery);
    }

    @Override
    public <T> List<T> find(Class<T> entityClass, Map<String, Object> criteria, int limit) {
        String selectQuery = SQL_QUERY_BUILDER.buildSqlSelectQuery(entityClass, criteria, limit);
        return prepareAndExecuteQuery(entityClass, criteria, selectQuery);
    }

    @Override
    public <T> List<T> find(Class<T> entityClass, int limit) {
        String selectQuery = SQL_QUERY_BUILDER.buildSqlSelectQuery(entityClass, limit);
        return prepareAndExecuteQuery(entityClass, null, selectQuery);
    }

    private <T> List<T> prepareAndExecuteQuery(Class<T> entityClass, Map<String, Object> criteria, String selectQuery) {
        try (var statement = connection.prepareStatement(selectQuery)){

            if(criteria != null) {
                STATEMENT_HANDLER.setStatementParameters(criteria, statement);
            }

            log.debug("Executing query...");
            ResultSet resultSet = statement.executeQuery();
            return RESULT_SET_PARSER.parseResultSet(entityClass, resultSet);

        } catch (SQLException ex) {
            throw new BibernateApplicationException("Cannot execute a query: " + selectQuery, ex);
        }
    }

    private <T> String retrieveIdFieldName(Class<T> entityClass) {
        log.debug("Retrieving id column name...");
        return Arrays.stream(entityClass.getDeclaredFields())
                .filter(field -> field.getDeclaredAnnotation(Id.class) != null)
                .map(Field::getName)
                .findFirst()
                .orElseThrow(() -> new NoIdFieldException("Please mark id column with Id.class annotation."));
    }
}
