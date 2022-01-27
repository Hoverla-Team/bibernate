package com.hoverla.bibernate.queryBuilder;

import com.hoverla.bibernate.queryBuilder.impl.SqlSelectQueryBuilderImpl;
import com.hoverla.bibernate.testutil.entity.Person;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class SqlSelectQueryBuilderTest {

    private final SqlSelectQueryBuilder sqlQueryBuilder = new SqlSelectQueryBuilderImpl();

    @Test
    public void buildSelectQuery() {
        String selectQuery = sqlQueryBuilder.buildSqlSelectQuery(Person.class);

        assertThat(selectQuery).isEqualTo("SELECT * FROM person");
    }

    @Test
    public void buildSelectQueryWithWhereClause() {
        String selectQuery = sqlQueryBuilder.buildSqlSelectQuery(Person.class, Map.of("name", "Bobo"));

        assertThat(selectQuery).isEqualTo("SELECT * FROM person WHERE name = ? ");
    }

    @Test
    public void buildSelectQueryWithWhereClauseAndLimit() {
        String selectQuery = sqlQueryBuilder.buildSqlSelectQuery(Person.class, Map.of("name", "Bobo"), 2);

        assertThat(selectQuery).isEqualTo("SELECT * FROM person WHERE name = ?  LIMIT 2");
    }

    @Test
    public void buildSelectQueryWithLimit() {
        String selectQuery = sqlQueryBuilder.buildSqlSelectQuery(Person.class, 2);

        assertThat(selectQuery).isEqualTo("SELECT * FROM person LIMIT 2");
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
