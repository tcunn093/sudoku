package com.tcunn.sudoku.entity;

import java.util.List;
import java.util.UUID;

import org.springframework.data.redis.core.RedisHash;

import com.tcunn.sudoku.SudokuBoardImpl;

@RedisHash
public class MaskedGame extends Game {

    private List<List<Integer>> mask;

    public MaskedGame(){
        super();
    }

    public MaskedGame(Game game, List<List<Integer>> mask){
        super(game.getId(), game.getBoard(), game.isSolved());
        this.mask = mask;
    }

    public MaskedGame(SudokuBoardImpl sudoku){
        super(UUID.randomUUID().toString(), sudoku.getBoardData(), sudoku.isSolved());
        this.mask = sudoku.getMask();
    }

    public List<List<Integer>> getMask() {
        return mask;
    }

    public void setMask(List<List<Integer>> mask) {
        this.mask = mask;
    }
}
