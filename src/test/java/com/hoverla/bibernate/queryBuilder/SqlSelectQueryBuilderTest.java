package com.hoverla.bibernate.queryBuilder;

import com.hoverla.bibernate.queryBuilder.impl.SqlSelectQueryBuilderImpl;
import com.hoverla.bibernate.testutil.entity.User;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SqlSelectQueryBuilderTest {

    private final SqlSelectQueryBuilder sqlQueryBuilder = new SqlSelectQueryBuilderImpl();

    @Test
    public void buildSelectQuery() {
        String selectQuery = sqlQueryBuilder.buildSqlSelectQuery(User.class);

        assertThat(selectQuery).isEqualTo("SELECT * FROM users");
    }

    @Test
    public void buildSelectQueryWithWhereClause() {
        String selectQuery = sqlQueryBuilder.buildSqlSelectQuery(User.class, Map.of("name", "Bobo"));

        assertThat(selectQuery).isEqualTo("SELECT * FROM users WHERE name = ? ");
    }

    @Test
    public void buildSelectQueryWithWhereClauseAndLimit() {
        String selectQuery = sqlQueryBuilder.buildSqlSelectQuery(User.class, Map.of("name", "Bobo"), 2);

        assertThat(selectQuery).isEqualTo("SELECT * FROM users WHERE name = ?  LIMIT 2");
    }

    @Test
    public void buildSelectQueryWithLimit() {
        String selectQuery = sqlQueryBuilder.buildSqlSelectQuery(User.class, 2);

        assertThat(selectQuery).isEqualTo("SELECT * FROM users LIMIT 2");
    }

    @Test
    public void buildSelectQueryWhenAnnotationTableIsNotSpecified() {
        String selectQuery = sqlQueryBuilder.buildSqlSelectQuery(Address.class);

        assertThat(selectQuery).isEqualTo("SELECT * FROM address");
    }

    private static class Address {
        private Long id;
        private String city;
    }

}
