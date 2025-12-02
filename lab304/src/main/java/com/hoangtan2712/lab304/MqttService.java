package com.hoangtan2712.lab304;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MqttService implements MqttCallback {

    @Value("${mqtt.broker}")
    private String broker;

    @Value("${mqtt.client-id}")
    private String clientId;

    private MqttClient client;

    @PostConstruct
    public void init() throws MqttException {
        client = new MqttClient(broker, clientId, new MemoryPersistence());
        MqttConnectOptions opt = new MqttConnectOptions();
        opt.setCleanSession(false);           // quan trọng cho Lab 3.04
        opt.setAutomaticReconnect(true);
        opt.setConnectionTimeout(10);

        client.setCallback(this);
        client.connect(opt);
        System.out.println("MQTT Connected - Broker: " + broker + " | ClientID: " + clientId);
    }

    // Publish linh hoạt bất kỳ topic nào
    public void publish(String topic, String payload, int qos, boolean retained) throws MqttException {
        if (!client.isConnected()) client.reconnect();

        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(qos);
        message.setRetained(retained);

        client.publish(topic, message);
        System.out.println("Published → [" + topic + "] QoS=" + qos + " Retained=" + retained + " | " + payload);
    }

    // Subscribe tạm thời (dùng trong API /subscribe)
    public void subscribeTemporary(String topic, IMqttMessageListener listener) throws MqttException {
        if (!client.isConnected()) client.reconnect();
        client.subscribe(topic, listener);
    }

    // Hủy subscribe (rất quan trọng để không leak)
    public void unsubscribe(String topic) throws MqttException {
        if (client.isConnected()) {
            client.unsubscribe(topic);
            System.out.println("Unsubscribed from: " + topic);
        }
    }

    @PreDestroy
    public void destroy() throws MqttException {
        if (client != null && client.isConnected()) {
            client.disconnect();
            System.out.println("MQTT Disconnected");
        }
    }

    @Override public void connectionLost(Throwable cause) {
        System.out.println("MQTT Connection lost: " + cause.getMessage());
    }

    @Override public void messageArrived(String topic, MqttMessage message) {
        // Không dùng ở đây vì Lab 3.04 dùng listener riêng
    }

    @Override public void deliveryComplete(IMqttDeliveryToken token) {}
}