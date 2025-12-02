package com.hoangtan1911.lab306;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/mqtt")
@CrossOrigin(origins = "http://localhost:3000")  
public class MqttPublishController {

    @Autowired
    private MqttPahoMessageHandler messageHandler;  // Được inject từ MqttConfig

    // ENDPOINT DÀNH RIÊNG CHO POSTMAN – TRANG 127
    @PostMapping("/publish")
    public String publishFromPostman(@RequestBody Map<String, String> request) {
        String topic = request.get("topic");
        String payload = request.get("payload");

        if (topic == null || payload == null) {
            return "Error: topic và payload không được để trống!";
        }

        Message<String> message = MessageBuilder
                .withPayload(payload)
                .setHeader("mqtt_topic", topic)
                .build();

        messageHandler.handleMessage(message);
        return "ĐÃ GỬI THÀNH CÔNG tới topic: " + topic + " | Payload: " + payload;
    }
}