
package com.hoangtan1911.lab306;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHandler;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class MqttPublisherService {

    @Autowired
    private MessageHandler mqttOutboundHandler;

    public void publish(String topic, String payload) {
        Message<String> message = MessageBuilder
                .withPayload(payload)
                .setHeader("mqtt_topic", topic)
                .build();

        mqttOutboundHandler.handleMessage(message);

        System.out.println("Published to " + topic + ": " + payload);
    }
}