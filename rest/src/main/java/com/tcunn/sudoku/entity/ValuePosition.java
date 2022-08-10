package com.tcunn.sudoku.entity;

import java.util.List;
import java.util.ArrayList;

public class ValuePosition {

    private final Integer value;
    private final List<Integer> position;

    public ValuePosition(Integer value, List<Integer> position) {
        this.value = value;
        this.position = position;
    }

    public ValuePosition(){
        this.value = null;
        this.position = new ArrayList<>();
    }

    public Integer getValue() {
        return value;
    }

    public List<Integer> getPosition() {
        return position;
    }

}
