package com.oshovskii.spring.webflux.r2dbc.demo.routes;

import com.oshovskii.spring.webflux.r2dbc.demo.domain.Message;
import com.oshovskii.spring.webflux.r2dbc.demo.handlers.MessageHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.oshovskii.spring.webflux.r2dbc.demo.routes.Router.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@RouterOperations(
        {
                @RouterOperation(
                        path = FIND_ALL_MESSAGES_ENDPOINT,
                        produces = {
                                MediaType.APPLICATION_JSON_VALUE
                        },
                        method = RequestMethod.GET,
                        beanClass = MessageHandler.class,
                        beanMethod = "findAll",
                        operation = @Operation(
                                operationId = "findAll",
                                responses = {
                                        @ApiResponse(
                                                responseCode = "200",
                                                description = "successful operation",
                                                content = @Content(schema = @Schema(
                                                        implementation = Message.class
                                                ))
                                        )
                                }
                        )
                ),
                @RouterOperation(
                        path = FIND_DATA_MESSAGE_ENDPOINT,
                        produces = {
                                MediaType.APPLICATION_JSON_VALUE
                        },
                        method = RequestMethod.GET,
                        beanClass = MessageHandler.class,
                        beanMethod = "findByData",
                        operation = @Operation(
                                operationId = "findByData",
                                responses = {
                                        @ApiResponse(
                                                responseCode = "200",
                                                description = "successful operation",
                                                content = @Content(schema = @Schema(
                                                        implementation = Message.class
                                                ))
                                        ),
                                        @ApiResponse(responseCode = "404",description = "message not found with given id")
                                },
                                parameters = {
                                        @Parameter(in = ParameterIn.PATH,name = "data")
                                }

                        )

                ),
                @RouterOperation(
                        path = MESSAGE_SAVE_ENDPOINT,
                        produces = {
                                MediaType.APPLICATION_JSON_VALUE
                        },
                        method = RequestMethod.POST,
                        beanClass = MessageHandler.class,
                        beanMethod = "save",
                        operation = @Operation(
                                operationId = "save",
                                responses = {
                                        @ApiResponse(
                                                responseCode = "200",
                                                description = "successful operation",
                                                content = @Content(schema = @Schema(
                                                        implementation = String.class
                                                ))
                                        )
                                },
                                requestBody = @RequestBody(
                                        content = @Content(schema = @Schema(
                                                implementation = Message.class
                                        ))
                                )

                        )


                )
        }
)
public @interface RouterApiInfo {
}
