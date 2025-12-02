package com.hoangtan2712.lab304;

import com.hoangtan2712.lab304.MqttConfig;
import com.hoangtan2712.lab304.MqttException;
import com.hoangtan2712.lab304.MqttPublishModel;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.concurrent.*;

@RestController
@RequestMapping("/api/mqtt")
public class MqttController {

    private final MqttConfig mqtt;

    public MqttController(MqttConfig mqtt) {
        this.mqtt = mqtt;
    }

    @PostMapping("/publish")
    public String publish(@RequestBody @Valid MqttPublishModel model) {
        try {
            MqttMessage msg = new MqttMessage(model.getMessage().getBytes());
            msg.setQos(model.getQos());
            msg.setRetained(model.getRetained());
            mqtt.publish(model.getTopic(), msg);
            return "Published successfully to " + model.getTopic();
        } catch (Exception e) {
            throw new MqttException("Publish failed: " + e.getMessage());
        }
    }

    @GetMapping("/subscribe")
    public List<MqttSubscribeModel> subscribe(
            @RequestParam String topic,
            @RequestParam(defaultValue = "10000") int waitMillis) {

        List<MqttSubscribeModel> list = new CopyOnWriteArrayList<>();
        CountDownLatch latch = new CountDownLatch(10);

        IMqttMessageListener listener = (t, msg) -> {
            MqttSubscribeModel m = new MqttSubscribeModel();
            m.setId(msg.getId());
            m.setMessage(new String(msg.getPayload()));
            m.setQos(msg.getQos());
            list.add(m);
            latch.countDown();
        };

        try {
            mqtt.subscribe(topic, listener);
            latch.await(waitMillis, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            throw new MqttException("Subscribe failed: " + e.getMessage());
        } finally {
            try { mqtt.unsubscribe(topic); } catch (Exception ignored) {}
        }
        return list;
    }
}