package com.hoangtan1911.lab306;
import com.hoangtan1911.lab306.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DeviceRepository extends JpaRepository<Device, Long> {
    Optional<Device> findByTopic(String topic);
}