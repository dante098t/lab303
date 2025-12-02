package com.hoangtan1911.lab306;
import com.hoangtan1911.lab306.Device;
import com.hoangtan1911.lab306.Telemetry;
import com.hoangtan1911.lab306.DeviceRepository;
import com.hoangtan1911.lab306.TelemetryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
@Component
public class MqttMessageHandler {

    @Autowired private DeviceRepository deviceRepository;
    @Autowired private TelemetryRepository telemetryRepository;

    @ServiceActivator(inputChannel = "mqttInputChannel")  // DÒNG QUAN TRỌNG NHẤT TRANG 128
    public void handleIncomingMessage(Message<?> message) {
        String topic = (String) message.getHeaders().get("mqtt_receivedTopic");
        String payload = message.getPayload().toString();

        System.out.println("NHẬN TELEMETRY từ topic: " + topic + " → " + payload);

        deviceRepository.findByTopic(topic).ifPresent(device -> {
            Telemetry telemetry = new Telemetry();
            telemetry.setDeviceId(device.getId());
            telemetry.setPayload(payload);
            telemetryRepository.save(telemetry);
            System.out.println("ĐÃ LƯU TELEMETRY cho thiết bị: " + device.getName() + " (ID=" + device.getId() + ")");
        });
    }
}