package com.hoverla.bibernate.util;

import com.hoverla.bibernate.annotation.Column;
import com.hoverla.bibernate.exception.BibernateApplicationException;
import com.hoverla.bibernate.exception.DefaultConstructorNotExistException;
import com.hoverla.bibernate.exception.FieldSetException;
import com.hoverla.bibernate.testutil.entity.Person;
import com.hoverla.bibernate.testutil.factory.PersonFactory;
import com.hoverla.bibernate.util.impl.ResultSetParserImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class ResultSetParserTest {

    private final ResultSetParser resultSetParser = new ResultSetParserImpl();

    @Test
    public void parseResultSet() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getObject("id")).thenReturn(PersonFactory.USER_ID);
        when(resultSet.getObject("first_name")).thenReturn(PersonFactory.USER_FIRST_NAME);
        when(resultSet.getObject("last_name")).thenReturn(PersonFactory.USER_LAST_NAME);
        when(resultSet.getObject("email")).thenReturn(PersonFactory.USER_EMAIL);

        List<Person> people = resultSetParser.parseResultSet(Person.class, resultSet);
        Person person = people.get(0);

        assertThat(people.size()).isEqualTo(1);
        assertThat(person).isEqualTo(PersonFactory.getTestUser());
    }

    @Test
    public void parseResultSetWithNoDefaultConstructor() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getObject("id")).thenReturn(1L);
        when(resultSet.getObject("name")).thenReturn("Order 1");

        assertThrows(
                DefaultConstructorNotExistException.class,
                () -> resultSetParser.parseResultSet(Order.class, resultSet),
                "Cannot find default constructor in entity class : \"Order\".");
    }

    @Test
    public void parseResultSetWithWrongColumnName() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getObject("id")).thenReturn(1L);
        when(resultSet.getObject("body_name")).thenThrow(new SQLException());

        assertThrows(
                FieldSetException.class,
                () -> resultSetParser.parseResultSet(Message.class, resultSet),
                "Cannot parse result set, column name : body_name");
    }

    @Test
    public void parseBadResultSet() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        when(resultSet.next()).thenThrow(new SQLException());

        assertThrows(
                BibernateApplicationException.class,
                () -> resultSetParser.parseResultSet(Message.class, resultSet),
                "Cannot parse and get next in result set.");
    }

    @AllArgsConstructor
    @Getter
    static class Order {
        private Long id;
        private String name;
    }

    @Getter
    @NoArgsConstructor
    static class Message {
        private Long id;
        @Column(name = "body_name")
        private String bodyName;
    }
}
