package com.tcunn.sudoku.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tcunn.sudoku.entity.MaskedGame;
import com.tcunn.sudoku.repository.GameRepository;

@Service
public class GameServiceImpl implements GameService{

    @Autowired private GameRepository gameRepository;

    @Override
    public MaskedGame save(MaskedGame game) {
        return gameRepository.save(game);
    }

    @Override
    public MaskedGame findById(String id) {
        return gameRepository.findById(id).get();
    }

    @Override
    public MaskedGame update(MaskedGame game, String id) {
        MaskedGame persistedGame = findById(id);

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
