package com.tcunn.sudoku.service;

import com.tcunn.sudoku.entity.Game;

public interface GameService {
    Game save(Game game);
    Game findById(String id);
    Game update(Game game, String id);
    void delete(String id);
}
