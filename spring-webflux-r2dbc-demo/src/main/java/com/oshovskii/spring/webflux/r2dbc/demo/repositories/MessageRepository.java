package com.oshovskii.spring.webflux.r2dbc.demo.repositories;

import com.oshovskii.spring.webflux.r2dbc.demo.domain.Message;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface MessageRepository extends ReactiveCrudRepository<Message, Long> {
    Flux<Message> findByData(String data);
}
