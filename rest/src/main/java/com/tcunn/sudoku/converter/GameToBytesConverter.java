package com.tcunn.sudoku.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcunn.sudoku.entity.Game;

@WritingConverter
public class GameToBytesConverter implements Converter<Game, byte[]> {

  private final Jackson2JsonRedisSerializer<Game> serializer;

  public GameToBytesConverter() {
    serializer = new Jackson2JsonRedisSerializer<Game>(Game.class);
    serializer.setObjectMapper(new ObjectMapper());
  }

  @Override
  public byte[] convert(Game value) {
    return serializer.serialize(value);
  }
}