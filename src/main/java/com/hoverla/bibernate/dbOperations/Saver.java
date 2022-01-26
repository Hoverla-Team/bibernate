package com.hoverla.bibernate.dbOperations;

import java.util.List;

public interface Saver {

    <T> T save(T objToSave);

    <T> List<T> saveAll(Iterable<T> entities);
}
