package com.tcunn.sudoku.entity;

import java.util.List;

import org.springframework.data.redis.core.RedisHash;

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

    public List<List<Integer>> getMask() {
        return mask;
    }

    public void setMask(List<List<Integer>> mask) {
        this.mask = mask;
    }
}
