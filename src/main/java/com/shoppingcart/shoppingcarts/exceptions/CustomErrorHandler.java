package com.shoppingcart.shoppingcarts.exceptions;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomErrorHandler {

    private String message;
    private LocalDateTime timestamp;
    private int status;
    private String stacktrace;


    public CustomErrorHandler(String message, int status, String stacktrace) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.stacktrace = stacktrace;
    }
}
