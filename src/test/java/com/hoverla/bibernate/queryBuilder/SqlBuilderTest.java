package com.hoverla.bibernate.queryBuilder;

import com.hoverla.bibernate.annotation.Table;
import com.hoverla.bibernate.queryBuilder.impl.SqlSaveQueryBuilder;

public class SqlBuilderTest {
    private SqlSaveQueryBuilder sqlSaveQueryBuilder = new SqlSaveQueryBuilder();



    public static class Order {}

    @Table("users")
    public static class User {}
}
