package com.oshovslii.spring.reactive.redis.config;

import com.oshovslii.spring.reactive.redis.models.Traveller;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class TravellerConfiguration {
    @Bean
    ReactiveRedisOperations<String, Traveller> redisOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<Traveller> serializer = new Jackson2JsonRedisSerializer<>(Traveller.class);

        RedisSerializationContext
                .RedisSerializationContextBuilder<String, Traveller> builder =
                RedisSerializationContext
                        .newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, Traveller> context =
                builder.key(new StringRedisSerializer())
                        .value(serializer)
                        .hashKey(new StringRedisSerializer())
                        .hashValue(new GenericJackson2JsonRedisSerializer())
                        .build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}
