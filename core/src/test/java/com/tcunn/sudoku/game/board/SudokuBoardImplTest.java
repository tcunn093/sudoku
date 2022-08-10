package com.tcunn.sudoku.game.board;

import static org.junit.Assert.assertTrue;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.tcunn.sudoku.SudokuBoardImpl;

public class SudokuBoardImplTest {

    private Entry<Integer, Integer> getFirstPositionWithValue(SudokuBoardImpl board){
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

        if(value == null){
            throw new IllegalArgumentException("Provided board does not have any set values.");
        }

        return new AbstractMap.SimpleEntry<Integer, Integer>(i, j);

    }

    private Entry<Integer, Integer> getFirstPositionWithNull(SudokuBoardImpl board){
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

        if(i == board.getBoardData().size() && j == board.getBoardData().size()){
            throw new IllegalArgumentException("Provided board does not have any null values.");
        }

        return new AbstractMap.SimpleEntry<Integer, Integer>(i, j);

    }

    @Test
    public void boardInitialisedToCompletionAndIsValid()
    {
        SudokuBoardImpl board = new SudokuBoardImpl();

        board.initialise();
        assertTrue(board.validate());
        assertTrue(!board.hasNulls());
    }

    @Test
    public void solvableBoardisValid(){

        SudokuBoardImpl board = new SudokuBoardImpl();

        board.initialise();
        board.makeSolvable();

        assertTrue(board.hasNulls());
        assertTrue(board.validate());

    }

    @Test
    public void canInsertValueIntoPosition(){

        SudokuBoardImpl board = new SudokuBoardImpl();

        board.initialise();
        board.makeSolvable();

        Map.Entry<Integer,Integer> position = getFirstPositionWithNull(board);

        board.mutate(3, position);

        assertTrue(board.getBoardData().get(position.getKey()).get(position.getValue()) == 3);

    }


    @Test
    public void canInsertNullIntoPosition(){
        SudokuBoardImpl board = new SudokuBoardImpl();

        board.initialise();
        //Do not make the board solvable for this test (board.makeSolvable()). 
        //We can insert a value at any position since no mask was generated.

        Map.Entry<Integer,Integer> position = getFirstPositionWithValue(board);

        board.mutate(null, position);

    }

    /**
     * Find the first position where the value is a mask value (not null after making the board solvable),
     * then attempt to insert a new value into that position. An exception should be thrown.
     */
    @Test(expected=InvalidMutationException.class)
    public void maskPreventsOverwritingInitialSudokuValues(){

        SudokuBoardImpl board = new SudokuBoardImpl();

        board.initialise();
        board.makeSolvable();

        Map.Entry<Integer,Integer> position = getFirstPositionWithValue(board);

        Integer value = board.getBoardData().get(position.getKey()).get(position.getValue());

        board.mutate(value == 9 ? 8 : value + 1, position);

    }

}