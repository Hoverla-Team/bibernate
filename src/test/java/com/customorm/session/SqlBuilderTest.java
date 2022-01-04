package com.customorm.session;

import com.hoverla.bibernate.annotation.Table;
import com.hoverla.bibernate.session.SqlBuilder;

public class SqlBuilderTest {
    private SqlBuilder sqlBuilder = new SqlBuilder();



    public static class Order {}

    @Table("users")
    public static class User {}
}
