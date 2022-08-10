package com.tcunn.sudoku.service;

public interface RestService<T, ID> {
    T create();
    T findById(ID id);
    T update(T entity, ID id);
    void delete(ID id);
}
