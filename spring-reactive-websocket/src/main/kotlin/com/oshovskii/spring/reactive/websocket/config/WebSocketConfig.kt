package com.oshovskii.spring.reactive.websocket.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.oshovskii.spring.reactive.websocket.model.FileEvent
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.channel.PublishSubscribeChannel
import org.springframework.integration.dsl.IntegrationFlows
import org.springframework.integration.file.dsl.Files
import org.springframework.messaging.Message
import org.springframework.messaging.MessageHandler
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketMessage
import org.springframework.web.reactive.socket.WebSocketSession
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import java.io.File
import java.util.concurrent.ConcurrentHashMap
import java.util.function.Consumer


@Configuration
class WebSocketConfig {

    @Bean
    fun incomingFileFlow (@Value("file:/directory") file : File) =
        IntegrationFlows
            .from(
                Files
                    .inboundAdapter(file)
                    .autoCreateDirectory(true)
            ) { p -> p.poller { pm -> pm.fixedRate(1000) } }
            .channel(incomingFilesChannels())
            .get()

    @Bean
    fun incomingFilesChannels() = PublishSubscribeChannel()

    @Bean
    fun webSocketHandler(): WebSocketHandler {
        val objectMapper = ObjectMapper()
        val connections = ConcurrentHashMap<String, MessageHandler>()

        class ForwardingMessageHandler(val session: WebSocketSession, val sink: FluxSink<WebSocketMessage>) : MessageHandler {

            private val sessionId = session.id

            override fun handleMessage(message: Message<*>) {
                val payload = message.payload as File
                val fileEvent = FileEvent(sessionId = sessionId, path = payload.absolutePath)
                val string = objectMapper.writeValueAsString(fileEvent)
                val textMessage = session.textMessage(string)
                sink.next(textMessage)
            }
        }

        return WebSocketHandler { session ->

            val publisher = Flux
                .create(Consumer<FluxSink<WebSocketMessage>> { sink ->
                    // register the connection to the client
                    // connect to the client
                    connections[session.id] = ForwardingMessageHandler(session, sink)
                    connections[session.id]?.let { incomingFilesChannels().subscribe(it) }
                })
                .doFinally {
                    connections[session.id]?.let { it1 -> incomingFilesChannels().unsubscribe(it1) }
                    connections.remove(session.id)
                }

            session.send(publisher)
        }
    }

    @Bean
    fun webSocketHandlerAdapter() = WebSocketHandlerAdapter()

    @Bean
    fun handlerMapping(): HandlerMapping {
        val simpleUrlHandlerMapping = SimpleUrlHandlerMapping()
        simpleUrlHandlerMapping.order = 10
        simpleUrlHandlerMapping.urlMap = mapOf("/ws/files" to webSocketHandler())
        return simpleUrlHandlerMapping;
    }
}
