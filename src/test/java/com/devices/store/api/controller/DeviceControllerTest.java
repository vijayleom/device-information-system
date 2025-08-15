package com.devices.store.api.controller;

import com.devices.store.api.entity.Device;
import com.devices.store.api.services.DeviceServices;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DeviceController.class)
class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DeviceServices deviceService;

    @Test
    void testGetAllDevices() throws Exception {
        Device device = new Device();
        device.setId(1L);
        Mockito.when(deviceService.getAllDevices()).thenReturn(Arrays.asList(device));

        mockMvc.perform(get("/devices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void testGetDeviceById_Found() throws Exception {
        Device device = new Device();
        device.setId(1L);
        Mockito.when(deviceService.getDevice(1L)).thenReturn(Optional.of(device));

        mockMvc.perform(get("/devices/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testGetDeviceById_NotFound() throws Exception {
        Mockito.when(deviceService.getDevice(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/devices/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateDevice() throws Exception {
        Device device = new Device();
        device.setId(1L);
        device.setState("IU");
        device.setBrand("testSamsung");
        device.setName("leo check");
        Mockito.when(deviceService.createDevice(any(Device.class))).thenReturn(device);

        mockMvc.perform(post("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"leo check\",\"state\":\"IU\",\"brand\":\"testSamsung\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testDeleteDevice() throws Exception {
        mockMvc.perform(delete("/devices/1"))
                .andExpect(status().isNoContent());
    }
}