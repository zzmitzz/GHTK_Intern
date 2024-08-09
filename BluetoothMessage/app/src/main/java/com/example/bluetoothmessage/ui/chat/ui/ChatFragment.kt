package com.example.bluetoothmessage.ui.chat.ui

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bluetoothmessage.base.BaseFragment
import com.example.bluetoothmessage.databinding.ChatchitFragmentBinding
import com.example.bluetoothmessage.ui.chat.adapter.MessageAdapter
import com.example.bluetoothmessage.ui.chat.viewmodel.ChatViewModel
import com.example.bluetoothmessage.view_model.ViewModelFactory

class ChatFragment : BaseFragment<ChatchitFragmentBinding, ChatViewModel>() {
    private var device: BluetoothDevice? = null
    private var mAdapter: MessageAdapter = MessageAdapter()
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

    override fun initBinding() {
        binding = ChatchitFragmentBinding.inflate(layoutInflater)
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

    override fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity(), ViewModelFactory())[ChatViewModel::class.java]
    }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args: ChatFragmentArgs by navArgs()
        device = args.device
        Log.d("TAG", "onCreate: ${device!!.name}")
        if (device == null)
            {
                Toast.makeText(requireContext(), "Device not found", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.apply {
            adapter = mAdapter
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        viewModel.apply {
            initBluetoothService {
                mAdapter.insertItem(it)
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun initListener() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.sendButton.setOnClickListener {
            val message = binding.editText.text.toString()
            viewModel.sendMessage(message)
            binding.editText.text.clear()
        }
        binding.nameTextView.text = device!!.name
    }

    override fun observeLoadingLivedata() {
        viewModel.isLoadding.observe(this) {
            if (it) {
                // Rush rush rush, no more time to configure the loading dialog
                Toast.makeText(requireContext(), "Start Connecting", Toast.LENGTH_SHORT).show()
                shitDialog.show()
            } else {
                shitDialog.dismiss()
            }
        }
    }

    override fun initObserver() {
    }
}
