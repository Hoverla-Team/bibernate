package com.hoverla.bibernate.session;

import com.hoverla.bibernate.dbOperations.impl.SaverImpl;
import com.hoverla.bibernate.testutil.entity.Person;
import com.hoverla.bibernate.testutil.factory.PersonFactory;
import com.hoverla.bibernate.util.DataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SessionTest {

    private final Connection connection = Mockito.mock(Connection.class);
    private final PreparedStatement statement = mock(PreparedStatement.class);
    private final HikariDataSource hikariDataSource = mock(HikariDataSource.class);
    private final ResultSet resultSet = mock(ResultSet.class);

    private final SaverImpl saverImpl = Mockito.mock(SaverImpl.class);;
    private final DataSource dataSource = Mockito.mock(DataSource.class);;
    private final Session session = new Session(dataSource);

    public SessionTest() throws SQLException {
    }

    @Test
    public void saveInsertEntityIntoDB() throws SQLException {
        Person newDefaultPerson = getMockedUser();
        newDefaultPerson.setId(null);

        Person savedPerson = session.save(newDefaultPerson);

        assertThat(savedPerson).isEqualTo(newDefaultPerson);
    }

    @Test
    public void findById() throws SQLException {
        Person person = getMockedUser();

        Person foundPerson = session.findById(Person.class, 1L).orElseThrow();

        AssertionsForClassTypes.assertThat(foundPerson).isEqualTo(person);
    }


    @Test
    public void find() throws SQLException {
        Person person = getMockedUser();

        List<Person> people = session.find(Person.class);

        AssertionsForClassTypes.assertThat(people.get(0)).isEqualTo(person);
    }

    @Test
    public void findWithLimit() throws SQLException {
        Person person = getMockedUser();

        List<Person> people = session.find(Person.class, 2);

        AssertionsForClassTypes.assertThat(people.size()).isEqualTo(2);
        AssertionsForClassTypes.assertThat(people.get(0)).isEqualTo(person);
    }

    @Test
    public void findWithCriteria() throws SQLException {
        Person person = getMockedUser();

        List<Person> people = session.find(Person.class, Map.of("id", 1L));

        AssertionsForClassTypes.assertThat(people.get(0)).isEqualTo(person);
    }

    @Test
    public void findWithCriteriaAndLimit() throws SQLException {
        Person person = getMockedUser();
        person.setId(2L);

        List<Person> people = session.find(Person.class, Map.of("id", 2L), 2);

        AssertionsForClassTypes.assertThat(people.get(0)).isEqualTo(person);
    }


    private Person getMockedUser() throws SQLException {
        Person person = PersonFactory.getTestUser();
        when(saverImpl.save(any())).thenReturn(person);
        when(connection.prepareStatement(anyString(),anyInt())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);
        when(statement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true);
        when(resultSet.getObject(anyInt())).thenReturn(1L);
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
}
