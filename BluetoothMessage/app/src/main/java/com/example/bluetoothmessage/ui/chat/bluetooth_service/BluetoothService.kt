package com.example.bluetoothmessage.ui.chat.bluetooth_service

import android.bluetooth.BluetoothSocket
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.example.bluetoothmessage.CONSTANT.MESSAGE_READ
import com.example.bluetoothmessage.CONSTANT.MESSAGE_TOAST
import com.example.bluetoothmessage.CONSTANT.MESSAGE_WRITE
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class BluetoothService(
    private val handler: Handler,
) {
    private var connectedThread: ConnectedThread? = null

    fun connect(socket: BluetoothSocket) {
        connectedThread = ConnectedThread(socket)
        connectedThread!!.start()
    }

    fun write(bytes: ByteArray) {
        connectedThread!!.write(bytes)
    }

    private inner class ConnectedThread(
        private val mSocket: BluetoothSocket,
    ) : Thread() {
        private val mmInStream: InputStream = mSocket.inputStream
        private val mmOutStream: OutputStream = mSocket.outputStream
        private val mmBuffer: ByteArray = ByteArray(1024) // mmBuffer store for the stream

        override fun run() {
            var numBytes: Int // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs.
            while (true) {
                // Read from the InputStream.
                numBytes =
                    try {
                        mmInStream.read(mmBuffer)
                    } catch (e: IOException) {
                        Log.d("BluetoothService", "Input stream was disconnected", e)
                        break
                    }

                // Send the obtained bytes to the UI activity.
                val readMsg =
                    handler.obtainMessage(
                        MESSAGE_READ,
                        numBytes,
                        -1,
                        mmBuffer,
                    )
                readMsg.sendToTarget()
            }
        }

        // Call this from the main activity to send data to the remote device.
        fun write(bytes: ByteArray) {
            try {
                mmOutStream.write(bytes)
            } catch (e: IOException) {
                Log.e("BluetoothService", "Error occurred when sending data", e)

                // Send a failure message back to the activity.
                val writeErrorMsg = handler.obtainMessage(MESSAGE_TOAST)
                val bundle =
                    Bundle().apply {
                        putString("toast", "Couldn't send data to the other device")
                    }
                writeErrorMsg.data = bundle
                handler.sendMessage(writeErrorMsg)
                return
            }
            // Share the sent message with the UI activity.
            val writtenBuffer = bytes.copyOf()

            val writtenMsg =
                handler.obtainMessage(
                    MESSAGE_WRITE,
                    -1,
                    -1,
                    writtenBuffer,
                )
            writtenMsg.sendToTarget()
        }

        // Call this method from the main activity to shut down the connection.
        fun cancel() {
            try {
                mSocket.close()
            } catch (e: IOException) {
                Log.e("BluetoothService", "Could not close the connect socket", e)
            }
        }
    }
}
