package com.tcunn.sudoku.game.board;

public class InvalidMutationException extends IllegalArgumentException{

    public InvalidMutationException(){
        super();
    }

    public InvalidMutationException(String message){
        super(message);
    }
}
