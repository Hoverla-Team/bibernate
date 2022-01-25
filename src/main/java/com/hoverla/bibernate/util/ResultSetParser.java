package com.hoverla.bibernate.util;

import java.sql.ResultSet;
import java.util.List;

/**
 * {@link ResultSetParser} is interface used for parsing given {@link ResultSet} to a list of java objects.
 * @author Maryna Melnychuk
 * @since 01.01.2022
 **/
public interface ResultSetParser {

    /**
     * Parse given result set, and using Reflection API creates java object of type {@link T} with set
     * data from result set.
     *
     * @param entityClass - type of entity to create
     * @param resultSet - result set to parse
     * @return list of {@link T} java objects
     **/
    <T> List<T> parseResultSet(Class<T> entityClass, ResultSet resultSet);
}
