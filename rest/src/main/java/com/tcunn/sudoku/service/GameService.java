package com.tcunn.sudoku.service;

public interface GameService<T, ID, V, P> extends RestService<T, String>{
    public T updateValueAtPosition(ID id, V value, P position);
}
