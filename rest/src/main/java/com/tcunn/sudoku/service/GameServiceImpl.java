package com.tcunn.sudoku.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcunn.sudoku.entity.Game;
import com.tcunn.sudoku.repository.GameRepository;

@Service
public class GameServiceImpl implements GameService{

    @Autowired private GameRepository gameRepository;

    @Override
    public Game save(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public Game findById(String id) {
        return gameRepository.findById(id).get();
    }

    @Override
    public Game update(Game game, String id) {
        Game persistedGame = findById(id);

        if(Objects.nonNull(game.getBoard())){
            persistedGame.setBoard(game.getBoard());
        }

        return gameRepository.save(persistedGame);
    }

    @Override
    public void delete(String id) {
        gameRepository.deleteById(id);;
    }
    
}
