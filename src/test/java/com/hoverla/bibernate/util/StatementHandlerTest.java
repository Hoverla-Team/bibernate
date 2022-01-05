package com.hoverla.bibernate.util;

import com.hoverla.bibernate.exception.BibernateApplicationException;
import com.hoverla.bibernate.util.impl.StatementHandlerImpl;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class StatementHandlerTest {

    private final StatementHandler statementHandler = new StatementHandlerImpl();
    private final Connection connection = mock(Connection.class);
    private final PreparedStatement statement = mock(PreparedStatement.class);

    @Test
    public void setStatementParameters() throws SQLException {
        when(connection
                .prepareStatement("SELECT * FROM users WHERE name = ?"))
                .thenReturn(statement);
        Map<String, Object> parameters = Map.of("name", "Bobo");

        statementHandler.setStatementParameters(parameters, statement);

        verify(statement).setObject(1, "Bobo");
    }

    @Test
    public void setStatementParametersCouldNotSetValue() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        Map<String, Object> parameters = Map.of("name", "Bobo");

        doThrow(new SQLException()).when(statement).setObject(1, "Bobo");

        assertThrows(
                BibernateApplicationException.class,
                () -> statementHandler.setStatementParameters(parameters, statement),
                "Cannot set value name for statement.");
    }
}
