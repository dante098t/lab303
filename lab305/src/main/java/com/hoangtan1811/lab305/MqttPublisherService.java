package com.hoangtan1811.lab305;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;


@Service
public class MqttPublisherService {

    @Autowired
    private MessageChannel mqttOutboundChannel;

    public void publish(String message) {
        mqttOutboundChannel.send(MessageBuilder.withPayload(message).build());
    }
}