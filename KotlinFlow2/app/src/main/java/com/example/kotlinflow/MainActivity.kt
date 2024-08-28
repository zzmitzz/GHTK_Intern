package com.example.kotlinflow

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.kotlinflow.databinding.ActivityMainBinding
import com.example.kotlinflow.databinding.DialogAddItemBinding
import com.example.kotlinflow.model.Staff
import com.example.kotlinflow.ui.liststaff.StaffAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var mStaffAdapter: StaffAdapter
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        initializeView()
        initListener()
        collectFlow()

    }
    private fun initListener(){
        binding.searchBar.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.updateName(p0?.toString() ?: "")
            }

        })
        binding.addBtn.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_item,null)
            AlertDialog.Builder(this)
                .setTitle("Add more Staff")
                .setView(dialogView)
                .setPositiveButton("OK"){ _, _ ->
                    val name = dialogView.findViewById<EditText>(R.id.editTextName).text.toString()
                    val year = dialogView.findViewById<EditText>(R.id.editTextYear).text.toString()
                    val place = dialogView.findViewById<EditText>(R.id.editTextPlace).text.toString()
                    viewModel.addStaff(Staff(
                        name = name,
                        year = year,
                        birthPlace = place
                    ))
                }.show()
        }
    }
    private fun initializeView() {
        mStaffAdapter = StaffAdapter()
        mStaffAdapter.invoke = {
            viewModel.deleteStaff(it)
        }
        mStaffAdapter.insertData(viewModel.initSampleData())
        binding.recyclerView.apply {
            adapter = mStaffAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
        }
    }

    private fun collectFlow() {
        lifecycleScope.launch {
            viewModel.data.collect{
                mStaffAdapter.setData(it.toMutableList())
            }
        }
        lifecycleScope.launch {
            viewModel.searchFlow
                .collect {
                    viewModel.updateData(staff = it)
                }
        }
    }
}