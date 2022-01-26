package com.hoverla.bibernate.dbOperations;

import com.hoverla.bibernate.exception.NoIdFieldException;
import com.hoverla.bibernate.dbOperations.impl.FinderImpl;
import com.hoverla.bibernate.testutil.factory.UserFactory;
import com.hoverla.bibernate.testutil.entity.User;
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
        User user = getMockedUser();

        User foundUser = finder.findById(User.class, 1L).orElseThrow();

        assertThat(foundUser).isEqualTo(user);
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
        User user = getMockedUser();

        List<User> users = finder.find(User.class);

        assertThat(users.size()).isEqualTo(2);
        assertThat(users.get(0)).isEqualTo(user);
    }

    @Test
    public void findWithLimit() throws SQLException {
        User user = getMockedUser();

        List<User> users = finder.find(User.class, 2);

        assertThat(users.size()).isEqualTo(2);
        assertThat(users.get(0)).isEqualTo(user);
    }

    @Test
    public void findWithCriteria() throws SQLException {
        User user = getMockedUser();

        List<User> users = finder.find(User.class, Map.of("id", 1L));

        assertThat(users.size()).isEqualTo(2);
        assertThat(users.get(0)).isEqualTo(user);
    }

    @Test
    public void findWithCriteriaAndLimit() throws SQLException {
        User user = getMockedUser();

        List<User> users = finder.find(User.class, Map.of("id", 2L), 2);

        assertThat(users.size()).isEqualTo(2);
        assertThat(users.get(0)).isEqualTo(user);
    }

    private User getMockedUser() throws SQLException {
        User user = UserFactory.getTestUser();
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
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

    @AllArgsConstructor
    static class Order {
        private Long id;
        private String name;
    }
}
