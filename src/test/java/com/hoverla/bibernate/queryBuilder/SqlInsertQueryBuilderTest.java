package com.hoverla.bibernate.queryBuilder;

import com.hoverla.bibernate.annotation.Column;
import com.hoverla.bibernate.annotation.Id;
import com.hoverla.bibernate.annotation.Table;
import com.hoverla.bibernate.queryBuilder.impl.SqlInsertQueryBuilderImpl;
import com.hoverla.bibernate.testutil.entity.User;
import com.hoverla.bibernate.testutil.factory.UserFactory;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SqlInsertQueryBuilderTest {

    private final SqlInsertQueryBuilderImpl sqlInsertQueryBuilderImpl = new SqlInsertQueryBuilderImpl();

    @Test
    public void buildInsertQuery() {
        User user = new User();
        String insertQuery = sqlInsertQueryBuilderImpl.buildInsertQuery(user);

        assertThat("INSERT INTO users (first_name, last_name, email) VALUES (?, ?, ?)").isEqualTo(insertQuery);
    }

    @Test
    public void buildInsertQueryWhenTableNameIsNOTExplicitlySpecified() {
        Order order = new Order();
        String insertQuery = sqlInsertQueryBuilderImpl.buildInsertQuery(order);

        assertThat("INSERT INTO order (product, userName) VALUES (?, ?)").isEqualTo(insertQuery);
    }

    @Table("users")
    @Data
    private static class User{

        @Id
        private Long id;
        @Column(name = "first_name")
        private String firstName;
        @Column(name = "last_name")
        private String lastName;
        @Column(name = "email")
        private String email;
    }

    @Data
    private static class Order{

        private String id;
        private String product;
        private String userName;
    }
}
