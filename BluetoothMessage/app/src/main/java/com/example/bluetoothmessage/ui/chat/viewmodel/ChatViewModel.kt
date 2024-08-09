package com.example.bluetoothmessage.ui.chat.viewmodel

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bluetoothmessage.CONSTANT.MESSAGE_READ
import com.example.bluetoothmessage.CONSTANT.MESSAGE_TOAST
import com.example.bluetoothmessage.CONSTANT.MESSAGE_WRITE
import com.example.bluetoothmessage.CONSTANT.uuid
import com.example.bluetoothmessage.ui.chat.bluetooth_service.BluetoothClientManager
import com.example.bluetoothmessage.ui.chat.bluetooth_service.BluetoothServerManager
import com.example.bluetoothmessage.ui.chat.bluetooth_service.BluetoothService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

@SuppressLint("MissingPermission")
class ChatViewModel : ViewModel() {
    var isLoadding: MutableLiveData<Boolean> = MutableLiveData(false)
    var pairedDevice: MutableSet<BluetoothDevice> = mutableSetOf()
    val newDeviceDiscovered: MutableSet<BluetoothDevice> = mutableSetOf()
    private lateinit var clientConnection: BluetoothClientManager
    private lateinit var serverConnection: BluetoothServerManager
    var mSocket: MutableLiveData<BluetoothSocket?> = MutableLiveData()
    private lateinit var bluetoothService: BluetoothService

    fun findPairedDevice(
        bluetoothAdapter: BluetoothAdapter?,
        callback: () -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoadding.postValue(true)
            pairedDevice = bluetoothAdapter!!.bondedDevices
            withContext(Dispatchers.Main) {
                callback()
            }
            isLoadding.postValue(false)
        }
    }

    fun initBluetoothService(updateMessage: (com.example.bluetoothmessage.ui.chat.model.Message) -> Unit) {
        viewModelScope.launch {
            isLoadding.postValue(true)
            val handler =
                object : Handler(Looper.getMainLooper()) {
                    override fun handleMessage(msg: Message) {
                        when (msg.what) {
                            MESSAGE_WRITE -> {
                                val writeBuf = msg.obj as ByteArray
                                val a = String(writeBuf)
                                Log.d("TAG", "Send: $a")
                                updateMessage(
                                    com.example.bluetoothmessage.ui.chat.model.Message(
                                        a,
                                        0,
                                        true,
                                    ),
                                )
                            }

                            MESSAGE_READ -> {
                                val numBytes = msg.arg1
                                val buffer = msg.obj as ByteArray
                                // Process the received Bluetooth data here
                                Log.d("TAG", "Received $numBytes bytes: ${String(buffer)}")
                                updateMessage(
                                    com.example.bluetoothmessage.ui.chat.model.Message(
                                        String(buffer, 0, numBytes),
                                        0,
                                        false,
                                    ),
                                )
                            }

                            MESSAGE_TOAST -> {
                                val toast = msg.data.getString("toast")
                            }

                            else -> {
                                Log.d("TAG", "Received: Unknown message")
                            }
                        }
                    }
                }
            bluetoothService = BluetoothService(handler)
            withContext(Dispatchers.IO) {
                bluetoothService.connect(mSocket.value!!)
            }
            isLoadding.postValue(false)
        }
    }

    fun connectDevice(
        bluetoothAdapter: BluetoothAdapter,
        device: BluetoothDevice,
        uuid: UUID,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoadding.postValue(true)
            clientConnection =
                BluetoothClientManager(bluetoothAdapter, device, uuid) {
                    mSocket.postValue(it)
                }
            clientConnection.start()
            isLoadding.postValue(false)
        }
    }

    fun sendMessage(message: String) {
        bluetoothService.write(message.toByteArray())
    }

    fun getNewConnection(bluetoothAdapter: BluetoothAdapter) {
        serverConnection = BluetoothServerManager(bluetoothAdapter, uuid)
        serverConnection.invokde = {
            mSocket.postValue(it)
        }
        serverConnection.start()
    }
}
