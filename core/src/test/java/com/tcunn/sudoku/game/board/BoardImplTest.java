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

        assertTrue(!hasNull);
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
    public void testBasicMutation(){

        SudokuBoardImpl board = new SudokuBoardImpl();

        board.initialise();
        board.makeSolvable();

        int i = 0;
        int j = 0;

        for(i = 0; i < board.getBoardData().size(); i++){
            for(j = 0; j < board.getBoardData().size(); j++){
                if(board.getBoardData().get(i).get(j) == null){
                    break;
                }
            }
            if(board.getBoardData().get(i).get(j) == null){
                break;
            }
        }

        Map.Entry<Integer,Integer> position = new AbstractMap.SimpleEntry<Integer, Integer>(i, j);

        board.mutate(3, position);

        assertTrue(board.getBoardData().get(i).get(j) == 3);

    }

    /**
     * Find the first position where the value is a mask value (not null after making the board solvable),
     * then attempt to insert a new value into that position. An exception should be thrown.
     */
    @Test(expected=InvalidMutationException.class)
    public void verifyThatMaskPreventsOverwritingInitialSudokuValues(){

        SudokuBoardImpl board = new SudokuBoardImpl();

        board.initialise();
        board.makeSolvable();

        int i = 0;
        int j = 0;

        Integer value = null;

        for(i = 0; i < board.getBoardData().size(); i++){
            for(j = 0; j < board.getBoardData().size(); j++){
                value = board.getBoardData().get(i).get(j);

                if(value != null){
                    break;
                }
            }
            if(value != null){
                break;
            }
        }

        Map.Entry<Integer,Integer> position = new AbstractMap.SimpleEntry<Integer, Integer>(i, j);

        board.mutate(value == 9 ? 8 : value + 1, position);

    }
}