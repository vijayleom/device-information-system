package com.devices.store.api.services;

import com.devices.store.api.entity.Device;
import com.devices.store.api.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceServices {

    private final DeviceRepository deviceRepository;

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public Optional<Device> getDevice(Long id) {
        return deviceRepository.findById(id);
    }

    public List<Device> getDevicesByBrand(String brand) {
        return deviceRepository.findByBrand(brand);
    }

    public List<Device> getDevicesByState(String state) {
        return deviceRepository.findByState(state);
    }

    @Transactional
    public Device createDevice(@Valid Device device) {
        return deviceRepository.save(device); // creationTime will be set automatically as per schema.sql
    }

    @Transactional
    public Optional<Device> updateDevice(Long id, @Valid Device newDevice) {
        return deviceRepository.findById(id).map(existing -> {
            //Either "inactive" or "available" without name or brand should be performed first before performing update.
            if ("IU".equals(existing.getState())) {
                if ((newDevice.getName() != null && !newDevice.getName().equals(existing.getName())) ||
                        (newDevice.getBrand() != null && !newDevice.getBrand().equals(existing.getBrand()))) {
                    throw new ValidationException("Cannot update name/brand of the device which is already in use (IU).");
                }
            }
            if (!existing.getCreationTime().equals(newDevice.getCreationTime())) {
                throw new ValidationException("Creation time cannot be updated.");
            }
            newDevice.setLastPatchedTime(Instant.now());
            newDevice.setId(id);
            return deviceRepository.save(newDevice);
        });
    }

    @Transactional
    public Optional<Device> patchDevice(Long id, Device patch) {
        return deviceRepository.findById(id).map(existing -> {
            //Either "inactive" or "available" without name or brand should be performed first before performing patch.
            if ("IU".equals(existing.getState())) {
                if ((patch.getName() != null && !patch.getName().equals(existing.getName())) ||
                        (patch.getBrand() != null && !patch.getBrand().equals(existing.getBrand()))) {
                    throw new ValidationException("Cannot patch name/brand of the device which is already in use (IU).");
                }
            }
            // Prevent patching creation time
            if (patch.getCreationTime() != null &&
                    !patch.getCreationTime().equals(existing.getCreationTime())) {
                throw new ValidationException("Creation time cannot be updated.");
            }
            if (patch.getName() != null) existing.setName(patch.getName());
            if (patch.getBrand() != null) existing.setBrand(patch.getBrand());
            if (patch.getState() != null) existing.setState(patch.getState());
            existing.setLastPatchedTime(Instant.now());
            return deviceRepository.save(existing);
        });
    }

    public void deleteDevice(Long id) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Device not found in our inventory"));
        if ("IU".equals(device.getState())) {
            throw new ValidationException("Cannot delete a device which is in use (IU).");
        }
        deviceRepository.delete(device);
    }
}
