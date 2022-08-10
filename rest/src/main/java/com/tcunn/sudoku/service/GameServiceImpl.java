package com.tcunn.sudoku.service;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcunn.sudoku.SudokuBoardImpl;
import com.tcunn.sudoku.entity.Game;
import com.tcunn.sudoku.entity.MaskedGame;
import com.tcunn.sudoku.repository.GameRepository;

@Service
public class GameServiceImpl implements GameService<Game, String, Integer, List<Integer>>{

    @Autowired private GameRepository gameRepository;

    @Override
    public Game create() {

        SudokuBoardImpl sudoku = new SudokuBoardImpl();
        sudoku.initialise();
        sudoku.makeSolvable();

        MaskedGame game = new MaskedGame();
        game.setBoard(sudoku.getBoardData());
        game.setMask(sudoku.getMask());

        return gameRepository.save(game);
    }

    @Override
    public Game findById(String id) {
        return gameRepository.findById(id).get();
    }

    @Override
    public Game update(Game game, String id) {

        MaskedGame persistedGame = (MaskedGame) findById(id);

        //Throws an exception if the board violates the mask
        SudokuBoardImpl.checkMask(
            game.getBoard(), 
            persistedGame.getMask()
        );

        persistedGame.setBoard(
            game.getBoard()
        );

        return gameRepository.save(persistedGame);
    }

    @Override
    public void delete(String id) {
        gameRepository.deleteById(id);
    }

    @Override
    public Game updateValueAtPosition(String id, Integer value, List<Integer> positionData) {

        if(positionData.size() != 2){
            throw new IllegalArgumentException("Position argument does not have 2 values (for x and y respectively)");
        } 

        Map.Entry<Integer,Integer> position = new AbstractMap.SimpleEntry<Integer, Integer>(positionData.get(0), positionData.get(1));
        
        MaskedGame persistedGame = (MaskedGame) findById(id);

        SudokuBoardImpl sudoku = new SudokuBoardImpl(persistedGame.getBoard(), persistedGame.getMask());
        sudoku.mutate(value, position);

        persistedGame.setBoard(sudoku.getBoardData());

        return update(persistedGame, persistedGame.getId());
    }
    
}
