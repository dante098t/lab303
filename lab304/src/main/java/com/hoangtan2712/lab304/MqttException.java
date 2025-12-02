package com.hoangtan2712.lab304;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MqttException extends RuntimeException {
    public MqttException(String message) {
        super(message);
    }
}