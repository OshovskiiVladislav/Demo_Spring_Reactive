package com.oshovslii.spring.reactive.redis.config;

import com.oshovslii.spring.reactive.redis.models.Traveller;
import com.oshovslii.spring.reactive.redis.models.enums.Gender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.util.UUID;

import static com.oshovslii.spring.reactive.redis.models.enums.Gender.MALE;

@Component
@Slf4j
public class DataLoader {
    private final ReactiveRedisConnectionFactory factory;
    private final ReactiveRedisOperations<String, Traveller> travellerOps;

    public DataLoader(ReactiveRedisConnectionFactory factory, ReactiveRedisOperations<String, Traveller> travellerOps) {
        this.factory = factory;
        this.travellerOps = travellerOps;
    }

    @PostConstruct
    public void loadData() {
        Traveller traveller1 = Traveller.builder()
                .id(UUID.randomUUID().toString())
                .name("Jet Black Redis")
                .gender(MALE)
                .build();

        Traveller traveller2 = Traveller.builder()
                .id(UUID.randomUUID().toString())
                .name("Black Alert Redis")
                .gender(Gender.FEMALE)
                .build();

        Traveller traveller3 = Traveller.builder()
                .id(UUID.randomUUID().toString())
                .name("My dear Self Redis")
                .gender(Gender.FEMALE)
                .build();
        var travellers = Flux.just(traveller1, traveller2, traveller3);

        var redisDbSet = travellers.flatMap(traveller -> travellerOps.opsForValue().set(traveller.getId(), traveller));

        factory.getReactiveConnection().serverCommands().flushAll().thenMany(
                        redisDbSet.thenMany(travellerOps.keys("*"))
                                .flatMap(travellerOps.opsForValue()::get))
                .subscribe(traveller -> log.info(traveller.toString()));
    }
}
