package com.hoangtan1811.lab305;
import com.hoangtan1811.lab305.MqttPublisherService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/mqtt")
public class MqttController {

    private final MqttPublisherService publisher;

    public MqttController(MqttPublisherService publisher) {
        this.publisher = publisher;
    }

    @GetMapping("/publish")
    public String publish() {
        publisher.publish("Hi from the IoT application - HoangTan2712 Lab305");
        return "Message published via Spring Integration!";
    }
}