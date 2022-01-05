package com.hoverla.bibernate.util.impl;

import com.hoverla.bibernate.annotation.Column;
import com.hoverla.bibernate.exception.BibernateApplicationException;
import com.hoverla.bibernate.exception.DefaultConstructorNotExistException;
import com.hoverla.bibernate.exception.EntityCreationException;
import com.hoverla.bibernate.exception.FieldSetException;
import com.hoverla.bibernate.util.ResultSetParser;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * {@link ResultSetParserImpl} class is implementation of {@link ResultSetParser} interface.
 * @author Maryna Melnychuk
 * @since 01.01.2022
 **/
@Slf4j
public class ResultSetParserImpl implements ResultSetParser {

    @Override
    public <T> List<T> parseResultSet(Class<T> entityClass, ResultSet resultSet) {
        log.debug("Parsing result set and creating entities...");
        List<T> entities = new ArrayList<>();
        try {
            while (resultSet.next()) {
                T entity = createEntityInstance(entityClass);
                setEntityFields(entityClass, resultSet, entity);
                entities.add(entity);
            }
        } catch (SQLException ex) {
            throw new BibernateApplicationException("Cannot parse and get next in result set.", ex);
        }
        return entities;
    }

    private <T> T createEntityInstance(Class<T> entityClass) {
        try {
            var constructor = entityClass.getConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (NoSuchMethodException ex) {
            throw new DefaultConstructorNotExistException(String.format(
                    "Cannot find default constructor in entity class : \"%s\".",
                    entityClass.getSimpleName()), ex);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException ex) {
            throw new EntityCreationException(String.format("Cannot create new instance of class : \"%s\".",
                                        entityClass.getSimpleName()), ex);
        }
    }

    private <T> void setEntityFields(Class<T> entityClass, ResultSet resultSet, T entity) {
        Arrays.stream(entityClass.getDeclaredFields())
                .forEach(entityField -> setValueForEntityField(entityClass, resultSet, entity, entityField));
    }

    private <T> void setValueForEntityField(Class<T> entityClass, ResultSet resultSet, T entity, Field entityField) {
        String columnName = getEntityColumnName(entityField);
        Object value = retrieveValueFromResultSet(resultSet, columnName);
        setValueToField(entityClass, entity, entityField, columnName, value);
    }

    private String getEntityColumnName(Field entityField) {
        var columnAnnotation= entityField.getDeclaredAnnotation(Column.class);
        return Optional.ofNullable(columnAnnotation)
                .map(Column::name)
                .orElseGet(entityField::getName);
    }

    private Object retrieveValueFromResultSet(ResultSet resultSet, String columnName) {
        try {
            return resultSet.getObject(columnName);
        } catch (SQLException ex) {
            throw new FieldSetException(
                    String.format("Cannot parse result set, column name : \"%s\".", columnName), ex);
        }
    }

    private <T> void setValueToField(Class<T> entityClass, T entity, Field entityField, String columnName, Object value) {
        try {
            entityField.setAccessible(true);
            entityField.set(entity, value);
        } catch (IllegalAccessException ex) {
            throw new FieldSetException(String.format("Cannot set value into field \"%s\" in class \"%s\".",
                    columnName, entityClass.getSimpleName()), ex);
        }
    }
}
