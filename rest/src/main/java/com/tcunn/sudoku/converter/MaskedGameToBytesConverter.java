package com.tcunn.sudoku.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcunn.sudoku.entity.MaskedGame;

@WritingConverter
public class MaskedGameToBytesConverter implements Converter<MaskedGame, byte[]> {

  private final Jackson2JsonRedisSerializer<MaskedGame> serializer;

  public MaskedGameToBytesConverter() {
    serializer = new Jackson2JsonRedisSerializer<MaskedGame>(MaskedGame.class);
    serializer.setObjectMapper(new ObjectMapper());
  }

  @Override
  public byte[] convert(MaskedGame value) {
    return serializer.serialize(value);
  }
}