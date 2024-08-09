package com.example.bluetoothmessage.ui.chat.bluetooth_service

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.util.UUID

@SuppressLint("MissingPermission")
class BluetoothServerManager(
    bluetoothAdapter: BluetoothAdapter,
    uuid: UUID,
) : Thread() {
    private val mmServerSocket: BluetoothServerSocket? by lazy(LazyThreadSafetyMode.NONE) {
        bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(NAME, uuid)
    }

    var invokde: (BluetoothSocket) -> Unit = {}

    override fun run() {
        // Keep listening until exception occurs or a socket is returned.
        var shouldLoop = true
        while (shouldLoop) {
            Log.d("BluetoothServerManager", "Socket's accept() method succeeded")
            val socket: BluetoothSocket? =
                try {
                    mmServerSocket?.accept()
                } catch (e: IOException) {
                    Log.e("BluetoothServerManager", "Socket's accept() method failed", e)
                    shouldLoop = false
                    null
                }
            socket?.also {
                Log.d("BluetoothServerManager", "Socket's ${socket.isConnected}")
                invokde(it)
                mmServerSocket?.close()
                shouldLoop = false
            }
        }
    }

    // Closes the connect socket and causes the thread to finish.
    fun cancel() {
        try {
            mmServerSocket?.close()
            Log.d("BluetoothServerManager", "Socket's close() method succeeded")
        } catch (e: IOException) {
            Log.e(TAG, "Could not close the connect socket", e)
        }
    }

    companion object {
        val TAG = BluetoothServerManager::class.java.simpleName
        val NAME = "BluetoothChat"
    }
}
