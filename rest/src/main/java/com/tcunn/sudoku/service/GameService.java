package com.tcunn.sudoku.service;

import com.tcunn.sudoku.entity.MaskedGame;

public interface GameService {
    MaskedGame save(MaskedGame game);
    MaskedGame findById(String id);
    MaskedGame update(MaskedGame game, String id);
    void delete(String id);
}
