package com.example.bluetoothmessage.ui.chat.ui

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.bluetoothmessage.CONSTANT
import com.example.bluetoothmessage.CONSTANT.uuid
import com.example.bluetoothmessage.R
import com.example.bluetoothmessage.base.BaseFragment
import com.example.bluetoothmessage.databinding.ConnectionFragmentBinding
import com.example.bluetoothmessage.ui.chat.adapter.DeviceItemAdapter
import com.example.bluetoothmessage.ui.chat.bluetooth_service.BluetoothServerManager
import com.example.bluetoothmessage.ui.chat.viewmodel.ChatViewModel
import com.example.bluetoothmessage.view_model.ViewModelFactory

class ConnectionFragment : BaseFragment<ConnectionFragmentBinding, ChatViewModel>() {
    private lateinit var pairedAdapter: DeviceItemAdapter
    private lateinit var newDeviceAdapter: DeviceItemAdapter
    private val newDeviceTrigger: MutableLiveData<Int> = MutableLiveData(0)
    private val newDevice: MutableSet<BluetoothDevice> = mutableSetOf()
    val bluetoothManager: BluetoothManager? by lazy {
        requireContext().getSystemService(BluetoothManager::class.java)
    }
    private var discoveryFinished: Boolean = false
    val bluetoothAdapter: BluetoothAdapter? by lazy {
        bluetoothManager
            ?.adapter
            .also {
                Log.d("TAG", "bluetoothAdapter: ${it.hashCode()}")
            }
    }
    private lateinit var getResult: ActivityResultLauncher<Intent>
    private lateinit var serverConnection: BluetoothServerManager
    private lateinit var dialog: AlertDialog
    private val receiver =
        object : BroadcastReceiver() {
            @SuppressLint("MissingPermission")
            override fun onReceive(
                context: Context,
                intent: Intent,
            ) {
                val action: String? = intent.action
                Log.d("ConnectionFragment", "onReceive: $action")
                when (action) {
                    BluetoothDevice.ACTION_FOUND -> {
                        Log.d("ConnectionFragment", "onReceive: ACTION_FOUND")
                        val device: BluetoothDevice? =
                            intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                        if (device != null) {
                            if (device.name != null) {
                                newDevice.add(device)
                                newDeviceTrigger.value = newDeviceTrigger.value?.plus(1)
                            }
                        }
                    }

                    BluetoothAdapter.ACTION_DISCOVERY_STARTED -> {
                        Log.d("ConnectionFragment", "onReceive: ACTION_DISCOVERY_STARTED")
                        Toast
                            .makeText(requireActivity(), "Discovery Started", Toast.LENGTH_SHORT)
                            .show()
                        binding.loadingBar.visibility = View.VISIBLE
                    }

                    BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                        if (!discoveryFinished) {
                            Toast
                                .makeText(
                                    requireActivity(),
                                    "Discovery Finished",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            bluetoothAdapter?.cancelDiscovery()
                            discoveryFinished = true
                        }
                        binding.loadingBar.visibility = View.GONE
                    }
                }
            }
        }
    private val shitDialog by lazy {
        val shitDialog =
            AlertDialog
                .Builder(requireContext())
                .setTitle("Loading...")
                .setCancelable(false)
                .create()
        val window = shitDialog.window
        window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
        )
        shitDialog
    }

    override fun initBinding() {
        binding = ConnectionFragmentBinding.inflate(layoutInflater)
        initRecyclerView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val filter =
            IntentFilter().apply {
                addAction(BluetoothDevice.ACTION_FOUND)
                addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
                addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
            }
        requireContext().registerReceiver(receiver, filter)
    }

    private fun initRecyclerView() {
        pairedAdapter = DeviceItemAdapter()
        newDeviceAdapter = DeviceItemAdapter()
        pairedAdapter.invoke = {
            viewModel.connectDevice(bluetoothAdapter!!, it, CONSTANT.uuid)
        }
        newDeviceAdapter.invoke = {
            viewModel.connectDevice(bluetoothAdapter!!, it, CONSTANT.uuid)
        }
        binding.recyclerView.apply {
            adapter = pairedAdapter
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }
        binding.recyclerView2.apply {
            adapter = newDeviceAdapter
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }
    }

    override fun initViewModel() {
        viewModel =
            ViewModelProvider(requireActivity(), ViewModelFactory())[ChatViewModel::class.java]
    }

    private fun updateDataDevice() {
        pairedAdapter.setData(viewModel.pairedDevice)
        newDeviceAdapter.setData(viewModel.newDeviceDiscovered)
    }

    @SuppressLint("MissingPermission")
    override fun initListener() {
        if (bluetoothAdapter == null) {
            Toast.makeText(requireActivity(), "Bluetooth not supported", Toast.LENGTH_SHORT).show()
        } else {
            Log.d("ConnectionFragment", "initListener: ${bluetoothAdapter!!.isEnabled}")
            if (bluetoothAdapter!!.isEnabled) {
                viewModel.findPairedDevice(bluetoothAdapter) {
                    updateDataDevice()
                }
                if (!bluetoothAdapter!!.isDiscovering) {
                    bluetoothAdapter!!.startDiscovery()
                }
            } else {
                val enableBluetooth = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                getResult =
                    registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                        if (it.resultCode == RESULT_OK) {
                            if (!bluetoothAdapter!!.isDiscovering) {
                                bluetoothAdapter!!.startDiscovery()
                            }
                            viewModel.also { vm ->
                                vm.findPairedDevice(bluetoothAdapter) {
                                    updateDataDevice()
                                }
                            }
                        } else {
                            getResult.launch(enableBluetooth)
                        }
                    }
                getResult.launch(enableBluetooth)
            }
        }
        binding.discovery.setOnClickListener {
            dialog =
                AlertDialog
                    .Builder(requireContext())
                    .setView(R.layout.loading_alert_dialog)
                    .setTitle("Looking for Ghtk-er")
                    .setPositiveButton("Cancle") { dialog, _ ->
                        try {
                            serverConnection.cancel()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        dialog.dismiss()
                    }.setCancelable(false)
                    .create()
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )
            dialog.window?.setGravity(android.view.Gravity.CENTER_HORIZONTAL)
            val requestCode = 1
            val discoverableIntent: Intent =
                Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE).apply {
                    putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
                }
            requireActivity().startActivityForResult(discoverableIntent, requestCode)
            viewModel.getNewConnection(bluetoothAdapter!!)
            dialog.show()
        }
    }

    override fun initObserver() {
        viewModel.mSocket.observe(this) {
            if (it != null) {
                Log.d("ConnectionFragment", "initObserver: ${it.remoteDevice}")
                val action = ConnectionFragmentDirections.actionConnectionFragmentToChitchatFragment(it.remoteDevice)
                if (::dialog.isInitialized && dialog.isShowing) {
                    dialog.dismiss()
                }
                findNavController().navigate(action)
                viewModel.mSocket.postValue(null)
            }
        }
    }

    override fun observeLoadingLivedata() {
        viewModel.isLoadding.observe(this) {
            if (it) {
                // Rush rush rush, no more time to configure the loading dialog
                shitDialog.show()
            } else {
                shitDialog.dismiss()
            }
        }
        newDeviceTrigger.observe(this) {
            newDeviceAdapter.setData(newDevice)
        }
    }

    override fun onStop() {
        super.onStop()
        shitDialog.dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().unregisterReceiver(receiver)
    }
}
