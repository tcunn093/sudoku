package com.tcunn.sudoku.config;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcunn.sudoku.converter.BytesToMaskedGameConverter;
import com.tcunn.sudoku.converter.MaskedGameToBytesConverter;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Bean
    public RedisCustomConversions redisCustomConversions(){
        return new RedisCustomConversions(Arrays.asList(new MaskedGameToBytesConverter(), new BytesToMaskedGameConverter()));
    }

    @Bean
    public ObjectMapper objectMapper() {
        return Jackson2ObjectMapperBuilder.json().build();
    }

    @Bean
    public RedisStandaloneConfiguration redisStandaloneConfiguration() {

        String redisUrlString = System.getProperty("REDIS_URL");

        String hostname;
        int port;

        System.out.println("REDIS_URL: " + redisUrlString);

        if(redisUrlString != null){
            URL redisUrl;
            try {
                redisUrl = new URL(redisUrlString);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e.getMessage());
            }

            hostname = redisUrl.getHost();
            port = redisUrl.getPort();
        } else{
            hostname = "redis";
            port = 6379;
        }

        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(hostname, port);
        return redisStandaloneConfiguration;
    }
    
    @Bean
    public RedisConnectionFactory connectionFactory(RedisStandaloneConfiguration redisStandaloneConfiguration) {
        LettuceClientConfiguration configuration = LettuceClientConfiguration.builder().build();
        return new LettuceConnectionFactory(redisStandaloneConfiguration, configuration);
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer(objectMapper()));
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    public LettuceClientConfigurationBuilderCustomizer lettuceClientConfigurationBuilderCustomizer() {
        return clientConfigurationBuilder -> {
            if (clientConfigurationBuilder.build().isUseSsl()) {
                clientConfigurationBuilder.useSsl().disablePeerVerification();
            }
        };
    }

}
