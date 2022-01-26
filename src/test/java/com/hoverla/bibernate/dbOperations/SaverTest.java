package com.hoverla.bibernate.dbOperations;

import com.hoverla.bibernate.dbOperations.impl.SaverImpl;
import com.hoverla.bibernate.testutil.entity.User;
import com.hoverla.bibernate.testutil.factory.UserFactory;
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
        User newDefaultPerson = getMockedUser();
        newDefaultPerson.setId(null);

        User savedPerson = saver.save(newDefaultPerson);

        assertThat(savedPerson).isEqualTo(newDefaultPerson);
    }

    @Test
    public void saveAllInsertCollectionToDB() throws SQLException {
        List<User> users = generateUserList();

        List<User> savedUsers = saver.saveAll(users);

        assertThat(users).isEqualTo(savedUsers);
    }

    private ArrayList<User> generateUserList() throws SQLException {
        var users = new ArrayList<User>();
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

    private User getMockedUser() throws SQLException {
        User user = UserFactory.getTestUser();
        when(connection.prepareStatement(anyString(),anyInt())).thenReturn(statement);
        when(statement.executeUpdate()).thenReturn(1);
        when(statement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true);
        when(resultSet.getObject(anyInt())).thenReturn(1L);
        setupMockerUserResultSet();
        setupMockerUserResultSet();
        return user;
    }

    private void setupMockerUserResultSet() throws SQLException {
        when(resultSet.getObject("id")).thenReturn(UserFactory.USER_ID);
        when(resultSet.getObject("first_name")).thenReturn(UserFactory.USER_FIRST_NAME);
        when(resultSet.getObject("last_name")).thenReturn(UserFactory.USER_LAST_NAME);
        when(resultSet.getObject("email")).thenReturn(UserFactory.USER_EMAIL);
    }
}
