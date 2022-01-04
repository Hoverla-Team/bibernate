package com.hoverla.bibernate.session;

import com.hoverla.bibernate.annotation.Column;
import com.hoverla.bibernate.exception.ResultSetException;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResultSetParser {

    public <T> T parseResultSet(Class<T> type, ResultSet resultSet) {
        T entity = null;
        try {
            while(resultSet.next()) {

                entity = type.getDeclaredConstructor().newInstance();

                for (Field field : getFieldsFromObject(type)) {
                    field.setAccessible(true);
                    Optional<String> columnName = getColumnName(field);
                    if (columnName.isPresent()) {
                        Object object = resultSet.getObject(columnName.get());
                        field.set(entity, object);
                    }
                }
            }
        } catch (Exception e) {
            throw new ResultSetException("Error occurred during parsing result set", e);
        }
        return entity;
    }

    private List<Field> getFieldsFromObject(Class<?> objType) {
        return Stream.of(Arrays.asList(objType.getDeclaredFields()),
                Arrays.asList(objType.getSuperclass().getDeclaredFields()))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }

    public Optional<String> getColumnName(Field field) {
        return Optional.ofNullable(field.isAnnotationPresent(Column.class) ? field.getAnnotation(Column.class).name() : field.getName());
    }
}
