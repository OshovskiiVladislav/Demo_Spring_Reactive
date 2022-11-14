package com.oshovslii.spring.reactive.redis.controllers;

import com.oshovslii.spring.reactive.redis.models.Traveller;
import com.oshovslii.spring.reactive.redis.repositories.TravellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class TravellerController {
    private final TravellerRepository travellerRepository;
    private final ReactiveRedisOperations<String, Traveller> reactiveRedisOperations;

    @GetMapping()
    public Flux<Traveller> getTravellers() {
        return this.travellerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Traveller> findById(@PathVariable String id) {
        return this.travellerRepository.findById(id);
    }

    @PostMapping()
    public Mono<Long> save(@RequestBody Traveller traveller) {
        return  this.travellerRepository.save(traveller);
    }

    @DeleteMapping("/{id}")
    public Mono<Long> deleteById(@PathVariable String id) {
        return this.travellerRepository.deleteById(id);
    }
}
