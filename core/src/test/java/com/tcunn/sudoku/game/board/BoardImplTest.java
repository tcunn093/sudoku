package com.tcunn.sudoku.game.board;

import static org.junit.Assert.assertTrue;

import java.util.AbstractMap;
import java.util.Map;

import org.junit.Test;

import com.tcunn.sudoku.SudokuBoardImpl;

public class BoardImplTest 
{
    @Test
    public void validBoardInitialised()
    {
        SudokuBoardImpl board = new SudokuBoardImpl();

        board.initialise();
        assertTrue(board.validate());
    }

    @Test
    public void solvableBoardisValid(){

        SudokuBoardImpl board = new SudokuBoardImpl();

        board.initialise();
        board.makeSolvable();

        boolean hasNull = false;

        for(int i = 0; i < board.getBoardData().size(); i++){
            for(int j = 0; j < board.getBoardData().size(); j++){
                if(board.getBoardData().get(i).get(j) == null){
                    hasNull = true;
                }

                if(hasNull){
                    break;
                }
            }
            if(hasNull){
                break;
            }
        }

        assertTrue(hasNull);

        assertTrue(board.validate());

    }

    @Test
    public void testMutation(){

        SudokuBoardImpl board = new SudokuBoardImpl();

        board.initialise();
        board.makeSolvable();

        Map.Entry<Integer,Integer> position = new AbstractMap.SimpleEntry<Integer, Integer>(3, 4);

        board.mutate(6, position);

        assertTrue(board.getBoardData().get(3).get(4) == 6);

    }
}