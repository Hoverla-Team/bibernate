package com.hoverla.bibernate.session;

import com.hoverla.bibernate.util.DataSource;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link Session} provides a main API from the ORM. It uses {@link JdbcDao} for all operations. Session
 * itself handles everything related to entities. It stores all loaded entities in the map nad acts as cache when
 * the user is trying to load an entity.
 */
public class Session {
    private final JdbcDao jdbcDao;
    private final Map<EntityKey<?>,Object> cache = new HashMap<>();

    public Session(DataSource dataSource) throws SQLException {
        this.jdbcDao = new JdbcDao(dataSource.getConnection());
    }

    public <T> T save(T objToSave){
        return jdbcDao.save(objToSave);
    }
}
