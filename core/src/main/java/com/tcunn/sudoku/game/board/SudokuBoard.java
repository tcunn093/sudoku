package com.tcunn.sudoku.game.board;

import java.util.Map.Entry;

public interface SudokuBoard<T> extends Board<T, Entry<T, T>>{
    boolean isSolvable();
    boolean isSolved();
}
