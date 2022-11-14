package com.oshovslii.spring.reactive.redis.models;

import com.oshovslii.spring.reactive.redis.models.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Traveller implements Serializable {
    private String id;
    private String name;
    private Gender gender;
}
