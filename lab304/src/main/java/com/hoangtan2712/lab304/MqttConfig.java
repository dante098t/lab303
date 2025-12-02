package com.hoangtan2712.lab304;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.annotation.PreDestroy;



@Component
public class MqttConfig implements MqttCallback {

    private final MqttClient client;

    public MqttConfig(@Value("${mqtt.broker}") String broker,
                      @Value("${mqtt.client-id}") String clientId) throws MqttException {
        client = new MqttClient(broker, clientId, new MemoryPersistence());
        client.setCallback(this);

        MqttConnectOptions opt = new MqttConnectOptions();
        opt.setCleanSession(false);
        opt.setAutomaticReconnect(true);
        opt.setConnectionTimeout(10);
        opt.setKeepAliveInterval(60);

        client.connect(opt);
        System.out.println("MQTT Connected: " + broker + " | ClientID: " + clientId);
    }

    public void publish(String topic, MqttMessage message) throws MqttException {
        if (!client.isConnected()) client.reconnect();
        client.publish(topic, message);
    }

    public void subscribe(String topic, IMqttMessageListener listener) throws MqttException {
        if (!client.isConnected()) client.reconnect();
        client.subscribe(topic, listener);
    }

    public void unsubscribe(String topic) throws MqttException {
        if (client.isConnected()) client.unsubscribe(topic);
    }

    @PreDestroy
    public void disconnect() throws MqttException {
        if (client != null && client.isConnected()) {
            client.disconnect();
            System.out.println("MQTT Disconnected");
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("MQTT Connection lost! " + cause.getMessage());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        // Không dùng ở Lab 3.04 vì subscribe dùng listener riêng
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // Có thể để trống
    }
}