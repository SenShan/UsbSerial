package com.serial.demo.examples;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;

import com.usbserial.UsbSerialDriver;
import com.usbserial.UsbSerialProber;
import com.serial.demo.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SerialActiviy extends AppCompatActivity {

    private List<SerialBean> serialList = new ArrayList<>();
    private SerialAdapter serialAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial);
        recyclerView = findViewById(R.id.recycSerial);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        refresh();
    }

    private void refresh() {
        serialList.clear();
        UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        UsbSerialProber usbDefaultProber = UsbSerialProber.getDefaultProber();
        UsbSerialProber usbCustomProber = CustomProber.getCustomProber();
        for (UsbDevice device : usbManager.getDeviceList().values()) {
            UsbSerialDriver driver = usbDefaultProber.probeDevice(device);
            if (driver == null) {
                driver = usbCustomProber.probeDevice(device);
            }
            if (driver != null) {
                for (int port = 0; port < driver.getPorts().size(); port++) {
                    serialList.add(new SerialBean(device, port, driver));
                }
            } else {
                serialList.add(new SerialBean(device, 0, null));
            }
        }
        Log.e("len", serialList.size() + "");
        for (int i = 0; i < serialList.size(); i++) {
            UsbDevice device = serialList.get(i).getDevice();
            if (device.getVendorId() == 6790 && device.getProductId() == 29987) {
                Log.e("devList", device.getDeviceName() + "," + device.getVendorId() + "," + device.getProductId() + "," + device.getProductName());
            } else {
                serialList.remove(i);
                i--;
            }
        }
        serialAdapter = new SerialAdapter(serialList);
        recyclerView.setAdapter(serialAdapter);
        serialAdapter.setClickListener(new SerialAdapter.OnWifiClickListener() {
            @Override
            public void onClick(SerialBean wifiBean) {
                if (wifiBean.getDriver() == null) {
                    Toast.makeText(SerialActiviy.this, "no driver", Toast.LENGTH_SHORT).show();
                } else {
                    Bundle args = new Bundle();
                    args.putInt("device", wifiBean.getDevice().getDeviceId());
                    args.putInt("port", wifiBean.getPort());
                    args.putInt("baud", 9600);
                    args.putBoolean("withIoManager", true);
                    Intent intent = new Intent(SerialActiviy.this, SendActivity.class);
                    intent.putExtras(args);
                    startActivity(intent);
                }
            }
        });
    }
}
