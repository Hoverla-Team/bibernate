package com.hoverla.bibernate.dbOperations;

import com.hoverla.bibernate.dbOperations.impl.SaverImpl;
import com.hoverla.bibernate.testutil.entity.Person;
import com.hoverla.bibernate.testutil.factory.PersonFactory;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SaverTest {


    private final Connection connection = Mockito.mock(Connection.class);
    private final PreparedStatement statement = mock(PreparedStatement.class);
    private final ResultSet resultSet = mock(ResultSet.class);
    private final Saver saver = new SaverImpl(connection);



    @Test
    public void saveInsertEntityIntoDB() throws SQLException {
        Person newDefaultPerson = getMockedUser();
        newDefaultPerson.setId(null);

        Person savedPerson = saver.save(newDefaultPerson);

        assertThat(savedPerson).isEqualTo(newDefaultPerson);
    }

    @Test
    public void saveAllInsertCollectionToDB() throws SQLException {
        List<Person> people = generateUserList();

        List<Person> savedPeople = saver.saveAll(people);

        assertThat(people).isEqualTo(savedPeople);
    }

    private ArrayList<Person> generateUserList() throws SQLException {
        var users = new ArrayList<Person>();
        users.add(getMockedUser());
        users.add(getMockedUser());
        users.add(getMockedUser());
        users.add(getMockedUser());
        users.add(getMockedUser());
        users.add(getMockedUser());
        users.add(getMockedUser());
        users.add(getMockedUser());
        users.add(getMockedUser());
        users.add(getMockedUser());
        users.add(getMockedUser());
        users.add(getMockedUser());
        return users;
    }

    private Person getMockedUser() throws SQLException {
        Person person = PersonFactory.getTestUser();
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
