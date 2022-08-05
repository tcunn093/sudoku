package com.tcunn.sudoku.entity;

public class Value<T> {

    private final T value;

    public T getValue() {
        return value;
    }

    public Value(T value){
        this.value = value;
    }

    public Value(){
        this(null);
    }
    
}
