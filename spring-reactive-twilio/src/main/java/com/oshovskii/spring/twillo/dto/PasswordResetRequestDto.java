package com.oshovskii.spring.twillo.dto;

import lombok.Data;

@Data
public class PasswordResetRequestDto {
    //Destination
    private String phoneNumber;
    private String userName;
    private String oneTimePassword;
}
