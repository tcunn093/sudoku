package com.tcunn.sudoku.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BaseGame {
    protected String id;
    protected List<List<Integer>> board;

    public BaseGame(){
        this(UUID.randomUUID().toString(), new ArrayList<>());
    }

    public BaseGame(List<List<Integer>> board){
        this(UUID.randomUUID().toString(), board);
    }

    public BaseGame(String id, List<List<Integer>> board){
        this.id = id;
        this.board = board;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<List<Integer>> getBoard() {
        return board;
    }

    public void setBoard(List<List<Integer>> board) {
        this.board = board;
    }
}
