package com.hoverla.bibernate.queryBuilder;

public interface SqlInsertQueryBuilder extends SqlQueryBuilder{

    String buildInsertQuery(Object objToSave);
}
