package com.tcunn.sudoku.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.redis.core.RedisHash;

import com.tcunn.sudoku.SudokuBoardImpl;

@RedisHash
public class Game {
    
    private String id;
    private List<List<Integer>> board;
    private List<List<Integer>> mask;
    private boolean solved;

    public Game(){
        this(UUID.randomUUID().toString(), new ArrayList<>(), false, new ArrayList<>());
    }

    public Game(List<List<Integer>> board){
        this(UUID.randomUUID().toString(), board, false, new ArrayList<>());
    }

    public Game(List<List<Integer>> board, boolean solved){
        this(UUID.randomUUID().toString(), board, solved);
    }

    public Game(String id, List<List<Integer>> board, boolean solved){
        this(id, board, solved, new ArrayList<>());
    }

    public Game(String id, List<List<Integer>> board, boolean solved, List<List<Integer>> mask){
        this.id = id;
        this.board = board;
        this.solved = solved;
        this.mask = mask;
    }

    public Game(String id, SudokuBoardImpl sudoku){
        this(id, sudoku.getBoardData(), sudoku.isSolved(), sudoku.getMask());
    }

    public Game(SudokuBoardImpl sudoku){
        this(UUID.randomUUID().toString(), sudoku.getBoardData(), sudoku.isSolved(), sudoku.getMask());
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

    public List<List<Integer>> getMask() {
        return mask;
    }

    public void setMask(List<List<Integer>> mask) {
        this.mask = mask;
    }


   
}
