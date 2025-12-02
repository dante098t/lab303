package com.hoangtan1911.lab306;

import com.hoangtan1911.lab306.Telemetry;
import com.hoangtan1911.lab306.TelemetryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/telemetry")
public class TelemetryController {
@Autowired
private TelemetryRepository telemetryRepository;
@GetMapping("/{deviceId}")
public List<Telemetry> getByDevice(@PathVariable Long deviceId) {
return telemetryRepository.findByDeviceId(deviceId);
}
}