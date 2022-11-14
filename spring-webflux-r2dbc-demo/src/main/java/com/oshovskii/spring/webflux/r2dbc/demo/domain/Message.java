package com.oshovskii.spring.webflux.r2dbc.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    private Long id;

    @JsonProperty("data")
    private String data;

    public Message(String data) {
        this.data = data;
    }
}
