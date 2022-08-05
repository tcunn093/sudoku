package com.tcunn.sudoku.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcunn.sudoku.entity.MaskedGame;

@ReadingConverter
public class BytesToMaskedGameConverter implements Converter<byte[], MaskedGame> {

  private final Jackson2JsonRedisSerializer<MaskedGame> serializer;

  public BytesToMaskedGameConverter() {
    serializer = new Jackson2JsonRedisSerializer<MaskedGame>(MaskedGame.class);
    serializer.setObjectMapper(new ObjectMapper());
  }

  @Override
  public MaskedGame convert(byte[] value) {
    return serializer.deserialize(value);
  }
}
