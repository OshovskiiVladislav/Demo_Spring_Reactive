package com.oshovslii.spring.reactive.redis.repositories;

import com.oshovslii.spring.reactive.redis.models.Traveller;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public record TravellerRepository(ReactiveRedisOperations<String, Traveller> reactiveRedisOperations) {

    public Flux<Traveller> findAll() {
        return this.reactiveRedisOperations.keys("*")
                .flatMap(reactiveRedisOperations.opsForValue()::get);
    }

    public Mono<Traveller> findById(String id) {
        return this.reactiveRedisOperations.opsForValue().get(id);
    }

    public Mono<Long> save(Traveller traveller) {
        return this.reactiveRedisOperations.convertAndSend("travellers", traveller);
    }

    public Mono<Long> deleteById(String id) {
        return this.reactiveRedisOperations.delete(id);
    }
}
