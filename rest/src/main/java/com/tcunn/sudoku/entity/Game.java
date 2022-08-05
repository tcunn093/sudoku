package com.tcunn.sudoku.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.redis.core.RedisHash;

@RedisHash
public class Game {
    
    private String id;
    private List<List<Integer>> board;
    private boolean solved;

    public Game(){
        this.id = UUID.randomUUID().toString();
        this.board = new ArrayList<>();
        this.solved = false;
    }

    public Game(List<List<Integer>> board){
        this(UUID.randomUUID().toString(), board, false);
    }

    public Game(List<List<Integer>> board, boolean solved){
        this(UUID.randomUUID().toString(), board, solved);
    }

    public Game(String id, List<List<Integer>> board, boolean solved){
        this.id = id;
        this.board = board;
        this.solved = solved;
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

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

   
}
