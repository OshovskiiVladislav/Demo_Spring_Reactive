package com.oshovskii.spring.webflux.r2dbc.demo.handlers;

import com.oshovskii.spring.webflux.r2dbc.demo.domain.Message;
import com.oshovskii.spring.webflux.r2dbc.demo.exceptions.MessageApiException;
import com.oshovskii.spring.webflux.r2dbc.demo.repositories.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Service
@Slf4j
public record MessageHandler(MessageRepository messageRepository) {
    public Mono<ServerResponse> findAll() {
        log.debug("MessageHandler|findAll()");
        return ok().contentType(APPLICATION_JSON).body(messageRepository.findAll(), Message.class);
    }

    public Mono<ServerResponse> findByData(ServerRequest request) {

        log.debug("MessageHandler|findByData()");
        String data = request.pathVariable("data");

        return messageRepository.findByData(data)
                .switchIfEmpty(Mono.error(
                        new MessageApiException("Message not found with data [ " + data + " ]"))
                )
                .collectList()
                .flatMap(messages -> ok().bodyValue(messages));
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        log.debug("MessageHandler|save()");
        Mono<Message> messageMono = request.bodyToMono(Message.class).flatMap(messageRepository::save);
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(messageMono, Message.class);
    }

    public Mono<ServerResponse> deleteById(ServerRequest request) {

        log.debug("MessageHandler|deleteById()");
        String id = request.pathVariable("id");

        return messageRepository.findById(Long.valueOf(id))
                .switchIfEmpty(Mono.error(
                        new MessageApiException("Message not found with id [ " + id + " ]"))
                )
                .flatMap(message -> messageRepository.deleteById(Long.valueOf(id)))
                .flatMap(message -> ServerResponse.ok()
                        .body(BodyInserters.fromValue(message)));
    }
}
