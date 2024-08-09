package com.example.bluetoothmessage.ui.chat.bluetooth_service

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.util.UUID

@SuppressLint("MissingPermission")
class BluetoothClientManager(
    private val bluetoothAdapter: BluetoothAdapter,
    device: BluetoothDevice,
    uuid: UUID,
    val invokde: (BluetoothSocket) -> Unit,
) : Thread() {
    private val mmSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
        device.createRfcommSocketToServiceRecord(uuid)
    }

    public override fun run() {
        // Cancel discovery because it otherwise slows down the connection.
        bluetoothAdapter.cancelDiscovery()
        try {
            mmSocket?.connect()
            invokde(mmSocket!!)
            Log.d("BluetoothClientManager", "Socket's connect() method succeeded")
        } catch (e: IOException) {
            Log.e(TAG, "Could not connect to the client socket", e)
            try {
                mmSocket?.close()
            } catch (e2: IOException) {
                Log.e(TAG, "Could not close the client socket", e2)
            }
            return
        }
    }

    // Closes the client socket and causes the thread to finish.
    fun cancel() {
        try {
            mmSocket?.close()
        } catch (e: IOException) {
            Log.e(TAG, "Could not close the client socket", e)
        }
    }

    companion object {
        val TAG = BluetoothClientManager::class.java.simpleName
    }
}
