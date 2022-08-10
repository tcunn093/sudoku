package com.tcunn.sudoku.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Game extends BaseGame {

    private boolean solved;

    public Game(){
        this(UUID.randomUUID().toString(), new ArrayList<>(), false);
    }

    public Game(List<List<Integer>> board){
        this(UUID.randomUUID().toString(), board, false);
    }

    public Game(List<List<Integer>> board, boolean solved){
        this(UUID.randomUUID().toString(), board, solved);
    }

    public Game(String id, List<List<Integer>> board, boolean solved){
        super(id, board);
        this.solved = solved;
    }

    public Game(Game game){
        this(game.getId(), game.getBoard(), game.isSolved());
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }
   
}
