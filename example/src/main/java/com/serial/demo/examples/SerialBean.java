package com.serial.demo.examples;

import android.hardware.usb.UsbDevice;

import com.usbserial.UsbSerialDriver;

public class SerialBean {
    private UsbDevice device;
    private int port;
    private UsbSerialDriver driver;

    public SerialBean(UsbDevice device, int port, UsbSerialDriver driver) {
        this.device = device;
        this.port = port;
        this.driver = driver;
    }

    public UsbDevice getDevice() {
        return device;
    }

    public void setDevice(UsbDevice device) {
        this.device = device;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public UsbSerialDriver getDriver() {
        return driver;
    }

    public void setDriver(UsbSerialDriver driver) {
        this.driver = driver;
    }
}
