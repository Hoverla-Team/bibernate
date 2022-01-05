package com.hoverla.bibernate.util.impl;

import com.hoverla.bibernate.exception.BibernateApplicationException;
import com.hoverla.bibernate.util.StatementHandler;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * {@link StatementHandlerImpl} class is implementation of {@link StatementHandler} interface.
 * @author Maryna Melnychuk
 * @since 01.01.2022
 **/
@Slf4j
public class StatementHandlerImpl implements StatementHandler {
    @Override
    public void setStatementParameters(Map<String, Object> parameters, PreparedStatement statement) {
        log.debug("Setting parameters for a prepared statement...");
        List<Object> criteriaValues = new ArrayList<>(parameters.values());

        Stream.iterate(0, index -> index + 1)
                .limit(criteriaValues.size())
                .forEach(index -> seStatementParameter(statement, criteriaValues, index));
    }

    private void seStatementParameter(PreparedStatement statement, List<Object> parametersValues, Integer index) {
        try {
            statement.setObject(index +1, parametersValues.get(index));
        } catch (SQLException ex) {
            throw new BibernateApplicationException(String.format("Cannot set value %s for statement.",
                    parametersValues.get(index)), ex);
        }
    }
}
