package com.example.ghtk_intern_week2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ghtk_intern_week2.base.BaseViewModel
import com.example.ghtk_intern_week2.base.LoadingDialog
import com.example.ghtk_intern_week2.databinding.ActivityMainBinding
import com.example.ghtk_intern_week2.viewmodel.ViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    protected lateinit var viewModel: MainViewModel

    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var dialog: LoadingDialog
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigationView = binding.bottomNav
        setupWithNavController(bottomNavigationView, navController)
        dialog = LoadingDialog(this)
        initViewModel()
        observeLiveData()
    }
    private fun initViewModel(){
        viewModel = ViewModelProvider(this, ViewModelFactory())[MainViewModel::class.java]
        Log.d("TAG", "initViewModel: ${viewModel.hashCode()}")
    }
    private fun observeLiveData(){
        viewModel.isLoading.observe(this){
            Log.d("TAG", "observeLiveData: $it")
            if(it){
                dialog.showDialog()
            }else{
                dialog.dismissDialog()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("TAG", "onPause: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG", "onDestroy: ")
    }
}