package com.hoverla.bibernate.dbOperations;

import com.hoverla.bibernate.exception.NoIdFieldException;
import com.hoverla.bibernate.dbOperations.impl.FinderImpl;
import com.hoverla.bibernate.testutil.entity.Person;
import com.hoverla.bibernate.testutil.factory.PersonFactory;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FinderTest {

    private final Connection connection = mock(Connection.class);
    private final PreparedStatement statement = mock(PreparedStatement.class);
    private final ResultSet resultSet = mock(ResultSet.class);

    private final Finder finder = new FinderImpl(connection);

    @Test
    public void findById() throws SQLException {
        Person person = getMockedUser();

        Person foundPerson = finder.findById(Person.class, 1L).orElseThrow();

        assertThat(foundPerson).isEqualTo(person);
    }

    @Test
    public void findByIdWithoutIdField() {
        assertThrows(
                NoIdFieldException.class,
                () -> finder.findById(Order.class, 1L).orElseThrow(),
                "Please mark id column with Id.class annotation.");
    }

    @Test
    public void find() throws SQLException {
        Person person = getMockedUser();

        List<Person> people = finder.find(Person.class);

        assertThat(people.size()).isEqualTo(2);
        assertThat(people.get(0)).isEqualTo(person);
    }

    @Test
    public void findWithLimit() throws SQLException {
        Person person = getMockedUser();

        List<Person> people = finder.find(Person.class, 2);

        assertThat(people.size()).isEqualTo(2);
        assertThat(people.get(0)).isEqualTo(person);
    }

    @Test
    public void findWithCriteria() throws SQLException {
        Person person = getMockedUser();

        List<Person> people = finder.find(Person.class, Map.of("id", 1L));

        assertThat(people.size()).isEqualTo(2);
        assertThat(people.get(0)).isEqualTo(person);
    }

    @Test
    public void findWithCriteriaAndLimit() throws SQLException {
        Person person = getMockedUser();

        List<Person> people = finder.find(Person.class, Map.of("id", 2L), 2);

        assertThat(people.size()).isEqualTo(2);
        assertThat(people.get(0)).isEqualTo(person);
    }

    private Person getMockedUser() throws SQLException {
        Person person = PersonFactory.getTestUser();
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        setupMockerUserResultSet();
        setupMockerUserResultSet();
        return person;
    }

    private void setupMockerUserResultSet() throws SQLException {
        when(resultSet.getObject("id")).thenReturn(PersonFactory.USER_ID);
        when(resultSet.getObject("first_name")).thenReturn(PersonFactory.USER_FIRST_NAME);
        when(resultSet.getObject("last_name")).thenReturn(PersonFactory.USER_LAST_NAME);
        when(resultSet.getObject("email")).thenReturn(PersonFactory.USER_EMAIL);
    }

    @AllArgsConstructor
    static class Order {
        private Long id;
        private String name;
    }
}
