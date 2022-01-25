package com.hoverla.bibernate.finder;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.hoverla.bibernate.annotation.Id;
import com.hoverla.bibernate.exception.NoIdFieldException;

/**
 * {@link Finder} API allows to find entity by id, by specified criteria in form of key (column name)
 * and value (criteria value), and get several entities by specified limit.
 * @author Maryna Melnychuk
 * @since 01.01.2022
 **/
public interface Finder {

    /**
     * Find entity in the database by specified id. In case entity class does not have field annotated
     * with {@link Id} annotation method will throw {@link NoIdFieldException}.
     *
     * @param entityClass - type of entity
     * @param id - id value
     * @return {@link Optional} of entity
     **/
    <T> Optional<T> findById(Class<T> entityClass, Object id) throws NoIdFieldException;

    /**
     * Find all entities specific type in the database.
     *
     * @param entityClass - type of entity
     * @return list of all entities
     **/
    <T> List<T> find(Class<T> entityClass);

    /**
     * Find all entities in the database by specified criteria map, all items
     * in criteria joined with 'and' conjunction.
     *
     * @param entityClass - type of entity
     * @param criteria - criteria used to form where clause
     * @return list of entities
     **/
    <T> List<T> find(Class<T> entityClass, Map<String, Object> criteria);

    /**
     * Find all entities in the database by specified criteria map and limit, all items
     * in criteria joined with 'and' conjunction.
     *
     * @param entityClass - type of entity
     * @param criteria - criteria used to form where clause
     * @param limit - number of records to return
     * @return list of entities
     **/
    <T> List<T> find(Class<T> entityClass, Map<String, Object> criteria, int limit);

    /**
     * Find number of entities specified by limit.
     *
     * @param entityClass - type of entity
     * @param limit - number of records to return
     * @return list of entities
     **/
    <T> List<T> find(Class<T> entityClass, int limit);

}
