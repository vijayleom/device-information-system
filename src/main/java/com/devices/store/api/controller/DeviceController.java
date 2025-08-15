package com.devices.store.api.controller;

import com.devices.store.api.entity.Device;
import com.devices.store.api.services.DeviceServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceServices deviceService;

    @Operation(summary = "Onboards a new device", responses = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Validation failed")
    })
    @PostMapping
    public ResponseEntity<Device> createDevice(@Valid @RequestBody Device device) {
        return ResponseEntity.ok(deviceService.createDevice(device));
    }

    @Operation(summary = "Performs Full Update on device by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully updated"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<Device> updateDevice(
            @PathVariable Long id,
            @Valid @RequestBody Device device
    ) {
        return deviceService.updateDevice(id, device)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Performs Partial or patch Update on device by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Successfully patched"),
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Device> patchDevice(
            @PathVariable Long id,
            @RequestBody Device devicePatch
    ) {
        return deviceService.patchDevice(id, devicePatch)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Retrieves all the devices from the inventory", responses = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping
    public ResponseEntity<List<Device>> getAllDevices() {
        return ResponseEntity.ok(deviceService.getAllDevices());
    }

    @Operation(summary = "Retrieve the devices from the inventory based on ID", responses = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Device> getDevice(@PathVariable Long id) {
        return deviceService.getDevice(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Retrieves the devices from the inventory based on Brand", responses = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Device>> getDevicesByBrand(@PathVariable String brand) {
        return ResponseEntity.ok(deviceService.getDevicesByBrand(brand));
    }

    @Operation(summary = "Retrieves the devices from the inventory based on State", responses = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping("/state/{state}")
    public ResponseEntity<List<Device>> getDevicesByState(@PathVariable String state) {
        return ResponseEntity.ok(deviceService.getDevicesByState(state));
    }

    @Operation(summary = "Deletes the device from the inventory based on ID", responses = {
            @ApiResponse(responseCode = "204", description = "DELETED")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }
}