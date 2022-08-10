package com.tcunn.sudoku.config;

import java.net.URI;
import java.net.URISyntaxException;
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

    // @Bean
    // public RedisStandaloneConfiguration redisStandaloneConfiguration() {

    //     String redisUrlString = System.getenv("REDIS_URL");

    //     RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();

    //     System.out.println("REDIS_URL: " + redisUrlString);

    //     if(redisUrlString != null){
    //         URI redisUrl;
    //         try {
    //             redisUrl = new URI(redisUrlString);
    //         } catch (URISyntaxException e) {
    //             throw new RuntimeException(e.getMessage());
    //         }

    //         redisStandaloneConfiguration.setHostName(redisUrl.getHost());
    //         redisStandaloneConfiguration.setPort(redisUrl.getPort());

    //     } else{
    //         redisStandaloneConfiguration.setHostName("redis");
    //         redisStandaloneConfiguration.setPort(6379);
    //     }

    //     return redisStandaloneConfiguration;
    // }
    
    // @Bean
    // public RedisConnectionFactory connectionFactory(RedisStandaloneConfiguration redisStandaloneConfiguration) {
    //     LettuceClientConfiguration configuration = LettuceClientConfiguration.builder().build();
    //     return new LettuceConnectionFactory(redisStandaloneConfiguration, configuration);
    // }

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
