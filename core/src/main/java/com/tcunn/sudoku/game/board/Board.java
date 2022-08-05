package com.tcunn.sudoku.game.board;

public interface Board<V, P> {
    public void reset();
    public void initialise();
    public void mutate(V value, P position) throws InvalidMutationException;
}
