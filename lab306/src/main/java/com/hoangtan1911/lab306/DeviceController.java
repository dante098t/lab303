package com.hoangtan1911.lab306;

import com.hoangtan1911.lab306.Device;
import com.hoangtan1911.lab306.DeviceRepository;
import com.hoangtan1911.lab306.MqttPublishController;
import com.hoangtan1911.lab306.MqttPublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/devices")
@CrossOrigin(origins = "http://localhost:3000")
public class DeviceController {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private MqttPublisherService mqttPublisherService;
    @Autowired
    private MqttPahoMessageDrivenChannelAdapter mqttAdapter;
    @GetMapping
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }
    @PostMapping
    public Device createDevice(@RequestBody Device device) {
        Device saved = deviceRepository.save(device);
        
        mqttAdapter.addTopic(device.getTopic() + "/#", 1);
        System.out.println("Đã subscribe topic: " + device.getTopic());
        
        return saved;
    }

@PostMapping("/{id}/command")
public ResponseEntity<String> sendCommand(@PathVariable Long id, @RequestBody String payload) {
    Device device = deviceRepository.findById(id).orElse(null);
    if (device == null) return ResponseEntity.notFound().build();

    mqttPublisherService.publish(device.getTopic(), payload);

    return ResponseEntity.ok("Command sent");
}
}
