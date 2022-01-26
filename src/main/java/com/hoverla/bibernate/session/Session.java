package com.hoverla.bibernate.session;

import com.hoverla.bibernate.dbOperations.Finder;
import com.hoverla.bibernate.dbOperations.impl.SaverImpl;
import com.hoverla.bibernate.dbOperations.impl.FinderImpl;
import com.hoverla.bibernate.util.DataSource;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * {@link Session} provides a main API from the ORM. It uses {@link SaverImpl} for all operations. Session
 * itself handles everything related to entities. It stores all loaded entities in the map nad acts as cache when
 * the user is trying to load an entity.
 */
public class Session {
    private final SaverImpl saverImpl;
    private final Finder finder;

    public Session(DataSource dataSource) throws SQLException {
        this.saverImpl = new SaverImpl(dataSource.getConnection());
        this.finder = new FinderImpl(dataSource.getConnection());
    }

    public <T> T save(T objToSave) {
        return saverImpl.save(objToSave);
    }

    public <T> List<T> saveAll(Iterable<T> entities){
        return saverImpl.saveAll(entities);
    }

    public <T> Optional<T> findById(Class<T> entityClass, Object id) {
        return finder.findById(entityClass, id);
    }

    public <T> List<T> find(Class<T> entityClass) {
        return finder.find(entityClass);
    }

    public <T> List<T> find(Class<T> entityClass, Map<String, Object> criteria) {
        return finder.find(entityClass, criteria);
    }

    public <T> List<T> find(Class<T> entityClass, Map<String, Object> criteria, int limit) {
        return finder.find(entityClass, criteria, limit);
    }

    public <T> List<T> find(Class<T> entityClass, int limit) {
        return finder.find(entityClass, limit);

    }
}
