package com.walixiwa.model;

import org.teleal.cling.model.meta.Device;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class DeviceModel {
    private Device device;

    public DeviceModel(Device device) {
        this.device = device;
    }

    public Device getDevice() {
        return device;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceModel that = (DeviceModel) o;
        return device.equals(that.device);
    }

    @Override
    public int hashCode() {
        return device.hashCode();
    }

    public String getDeviceName() {
        String display;
        if (device.getDetails().getFriendlyName() != null) {
            display = getEncodedString(device.getDetails().getFriendlyName());
        } else {
            display = getEncodedString(device.getDisplayString());
        }
        return device.isFullyHydrated() ? display : display + " *";
    }

    private static String getEncodedString(String destination) {
        try {
            //判断当前字符串的编码格式
            if (destination.equals(new String(destination.getBytes("iso8859-1"), "iso8859-1"))) {
                destination = new String(destination.getBytes("iso8859-1"), StandardCharsets.UTF_8);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return destination;
    }

} 


