package com.tcunn.sudoku.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tcunn.sudoku.entity.Game;

@Repository
public interface GameRepository extends CrudRepository<Game, String>{}
