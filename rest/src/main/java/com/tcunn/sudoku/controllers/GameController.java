package com.tcunn.sudoku.controllers;

import java.util.AbstractMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tcunn.sudoku.SudokuBoardImpl;
import com.tcunn.sudoku.entity.Game;
import com.tcunn.sudoku.entity.Value;
import com.tcunn.sudoku.service.GameService;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GameController {
    
    @Autowired private GameService gameService;

    @GetMapping("/game/{id}")
    public Game findGameById(@PathVariable("id") String gameId){
        return gameService.findById(gameId);
    }

    @PostMapping("/game")
    public Game createGame(){

        SudokuBoardImpl sudoku = new SudokuBoardImpl();
        sudoku.initialise();
        sudoku.makeSolvable();

        Game game = new Game(sudoku.getBoardData(), sudoku.isSolved());

        return gameService.save(game);
    }

    @PutMapping("/game/{id}")
    public Game updateGame(@RequestBody Game game, @PathVariable("id") String gameId){
        return gameService.update(game, gameId);
    }

    @PatchMapping("/game/{id}/x/{xPos}/y/{yPos}")
    public Game updateValueAtPosition(@RequestBody Value<Integer> value, @PathVariable("id") String id, @PathVariable("xPos") int x, @PathVariable("yPos") int y){
        
        Game game = gameService.findById(id);
                
        Map.Entry<Integer,Integer> position = new AbstractMap.SimpleEntry<Integer, Integer>(x, y);

        SudokuBoardImpl sudoku = new SudokuBoardImpl(game.getBoard());
        sudoku.mutate(value.getValue(), position);
        
        Game newGame = new Game(id, sudoku.getBoardData(), sudoku.isSolved());
        
        return gameService.update(newGame, id);
    }

    @DeleteMapping("/game/{id}")
    public void deleteGameById(@PathVariable("id") String gameId){
        gameService.delete(gameId);
    }

    
}
