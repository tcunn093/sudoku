package com.tcunn.sudoku.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcunn.sudoku.entity.Game;

@ReadingConverter
public class BytesToGameConverter implements Converter<byte[], Game> {

  private final Jackson2JsonRedisSerializer<Game> serializer;

  public BytesToGameConverter() {
    serializer = new Jackson2JsonRedisSerializer<Game>(Game.class);
    serializer.setObjectMapper(new ObjectMapper());
  }

  @Override
  public Game convert(byte[] value) {
    return serializer.deserialize(value);
  }
}
