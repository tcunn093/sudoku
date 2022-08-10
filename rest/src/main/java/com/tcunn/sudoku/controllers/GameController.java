package com.tcunn.sudoku.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tcunn.sudoku.entity.BaseGame;
import com.tcunn.sudoku.entity.Game;
import com.tcunn.sudoku.entity.ValuePosition;
import com.tcunn.sudoku.service.GameService;

@RestController
public class GameController {
    
    @Autowired private GameService<Game, String, Integer, List<Integer>> gameService;

    @GetMapping("/game/{id}")
    public BaseGame findGameById(@PathVariable("id") String gameId){
        return gameService.findById(gameId);
    }

    @PostMapping("/game")
    public BaseGame createGame(){
        return gameService.create();
    }

    @PutMapping("/game/{id}")
    public BaseGame updateGame(@RequestBody Game game, @PathVariable("id") String gameId){
        return gameService.update(game, gameId);
    }

    @PatchMapping("/game/{id}")
    public BaseGame updateValueAtPosition(@RequestBody ValuePosition valuePosition, @PathVariable("id") String id){
        return gameService.updateValueAtPosition(id, valuePosition.getValue(), valuePosition.getPosition());
    }

    @DeleteMapping("/game/{id}")
    public void deleteGameById(@PathVariable("id") String gameId){
        gameService.delete(gameId);
    }

}
