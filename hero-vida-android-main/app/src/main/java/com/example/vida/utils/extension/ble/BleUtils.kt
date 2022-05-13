package com.example.vida.utils.extension.ble

import android.content.Context
import com.otcengineering.otcble.BleSDK
import com.otcengineering.otcble.Status
import com.otcengineering.otcble.interfaces.OnDeviceFound

class BleUtils {
    companion object{

        fun init(ctx: Context) {
            return BleSDK.init(ctx)
        }

        fun isBluetoothOn(): Boolean {
            return BleSDK.isBluetoothOn()
        }

        fun isConnected(): Boolean {
            return BleSDK.isConnected()
        }

        fun getConnectedMAC(): String? {
            return BleSDK.getConnectedMAC()
        }

        fun getStatus(): Status {
            return BleSDK.getStatus()
        }

        fun connect(device: String) {
            return BleSDK.connect(device)
        }

        fun disconnect() {
            return BleSDK.disconnect()
        }

        fun scan(callback: OnDeviceFound) {
            return BleSDK.scanDevices(callback)
        }

        fun enableBluetooth(ctx: Context) {
            return BleSDK.enableBluetooth(ctx)
        }
    }
}