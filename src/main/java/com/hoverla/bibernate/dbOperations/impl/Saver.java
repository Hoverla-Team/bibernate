package com.hoverla.bibernate.dbOperations.impl;

import com.hoverla.bibernate.exception.PersistenceException;
import com.hoverla.bibernate.queryBuilder.impl.SqlSaveQueryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class Saver {

    private final Connection connection;

    private final SqlSaveQueryBuilder sqlSaveQueryBuilder;

    public Saver(Connection connection) {
        this.connection = connection;
        this.sqlSaveQueryBuilder = new SqlSaveQueryBuilder();
    }

    public <T> T save(T objToSave) {
        try (connection) {
            String insertQuery = sqlSaveQueryBuilder.buildSaveQuery(objToSave);
            try (var statement = connection.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
                setObjectParameterValuesIntoStatement(objToSave, statement);
                log.info("Bibernate: {}", insertQuery);
                log.info("Executing INSERT query");
                statement.executeUpdate();
                setIdFromDBToObject(objToSave, statement);
            }
        } catch (SQLException | IllegalAccessException | NoSuchFieldException e) {
            throw new PersistenceException("Error while saving object: " + objToSave, e);
        }
        return objToSave;
    }

    private <T> void setObjectParameterValuesIntoStatement(T objToSave, PreparedStatement statement) throws SQLException, IllegalAccessException {
        Field[] fields = objToSave.getClass().getDeclaredFields();
        for (int i = 1; i < fields.length; i++) {
            fields[i].setAccessible(true);
            statement.setObject(i, fields[i].get(objToSave));
        }
    }

    private <T> void setIdFromDBToObject(T objToSave, PreparedStatement statement) throws SQLException, NoSuchFieldException, IllegalAccessException {
        ResultSet generatedKeys = statement.getGeneratedKeys();
        Object id;
        if (generatedKeys.next()) {
            id = generatedKeys.getObject(1);
            log.info("Setting generated id into object: {}", id.toString());
            Field newId = objToSave.getClass().getDeclaredField("id");
            newId.setAccessible(true);
            newId.set(objToSave, id);
        } else
            throw new PersistenceException("Cannot obtain new ID from DB");
    }
}
