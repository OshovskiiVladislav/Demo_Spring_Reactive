package com.oshovskii.spring.webflux.r2dbc.demo.routes;

import com.oshovskii.spring.webflux.r2dbc.demo.handlers.GreetingHandler;
import com.oshovskii.spring.webflux.r2dbc.demo.handlers.MessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

@Configuration
public class Router {

    protected static final String HELLO_ENDPOINT = "/hello";
    protected static final String FIND_ALL_MESSAGES_ENDPOINT = "/";
    protected static final String FIND_DATA_MESSAGE_ENDPOINT = "/{data}";
    protected static final String MESSAGE_SAVE_ENDPOINT = "/";
    protected static final String MESSAGE_DELETE_BY_ID_ENDPOINT = "/{id}";
    protected static final String MAIN_ENDPOINT = "/";

    @Bean
    @RouterApiInfo
    public RouterFunction<ServerResponse> route(GreetingHandler greetingHandler, MessageHandler messageHandler) {
        RequestPredicate routeHello = RequestPredicates
                .GET(HELLO_ENDPOINT)
                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN));

        RequestPredicate routeAllMessages = RequestPredicates
                .GET(FIND_ALL_MESSAGES_ENDPOINT)
                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON));

        RequestPredicate routeFindMessageByText = RequestPredicates
                .GET(FIND_DATA_MESSAGE_ENDPOINT)
                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON));

        RequestPredicate routeSaveMessage = RequestPredicates.POST(MESSAGE_SAVE_ENDPOINT)
                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON));

        RequestPredicate routeDeleteMessage = RequestPredicates.DELETE(MESSAGE_DELETE_BY_ID_ENDPOINT);

        RequestPredicate routeMain = RequestPredicates.GET(MAIN_ENDPOINT);

        return RouterFunctions
                .route(routeHello, greetingHandler::hello)
                .andRoute(routeAllMessages, request -> messageHandler.findAll())
                .andRoute(routeFindMessageByText, messageHandler::findByData)
                .andRoute(routeSaveMessage, messageHandler::save)
                .andRoute(routeDeleteMessage, messageHandler::deleteById)
                .andRoute(
                        routeMain,
                        greetingHandler::index
                );
    }
}
