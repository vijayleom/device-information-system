package com.devices.store.api.services;

import com.devices.store.api.entity.Device;
import com.devices.store.api.repository.DeviceRepository;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(DeviceServices.class)
public class DeviceServicesTest {

    @Autowired
    private DeviceServices deviceServices;

    @Autowired
    private DeviceRepository deviceRepository;

    @Test
    void testCreateAndGetDevice() {
        Device device = new Device();
        device.setName("leo check");
        device.setState("IU");
        device.setBrand("testSamsung");
        device = deviceServices.createDevice(device);

        Optional<Device> found = deviceServices.getDevice(device.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("leo check");
        assertThat(found.get().getState()).isEqualTo("IU");
        assertThat(found.get().getBrand()).isEqualTo("testSamsung");
    }

    @Test
    void testGetAllDevices() {
        Device device1 = new Device();
        device1.setBrand("testSamsung");
        device1.setName("leo check");
        device1.setState("IA");
        deviceRepository.save(device1);

        Device device2 = new Device();
        device2.setBrand("testNokia");
        device2.setName("leo check");
        device2.setState("AV");
        deviceRepository.save(device2);

        assertThat(deviceServices.getAllDevices()).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void testDeleteDeviceException() {
        String expectedMessage = "Cannot delete a device which is in use (IU).";
        Device device = new Device();
        device.setBrand("testSamsung");
        device.setName("leo check");
        device.setState("IU");
        Device finalDevice = deviceRepository.save(device);

        ValidationException exception = Assertions.assertThrows(
                ValidationException.class,
                () -> deviceServices.deleteDevice(finalDevice.getId())
        );
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testDeleteDevice() {
        String expectedMessage = "Cannot delete a device which is in use (IU).";
        Device device = new Device();
        device.setBrand("testSamsung");
        device.setName("leo check");
        device.setState("AV");
        Device finalDevice = deviceRepository.save(device);

        deviceServices.deleteDevice(finalDevice.getId());
        assertThat(deviceRepository.findById(device.getId())).isNotPresent();
    }
}