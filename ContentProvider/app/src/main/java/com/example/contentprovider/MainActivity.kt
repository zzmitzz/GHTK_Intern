package com.example.contentprovider

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.contentprovider.databinding.ActivityMainBinding
import com.example.contentprovider.ui.contacts.ContactFragment

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel by lazy {
        ViewModelProvider(this, ViewModelFactory(application))[MainViewModel::class.java]
    }
    private val loadingDialog by lazy {
        AlertDialog
            .Builder(this)
            .setTitle("Loading")
            .setCancelable(false)
            .create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        observeLoadingData()
        navigateFragment1()
        userPermission()
    }

    private fun userPermission() {
        requestPermissions(PERMISSION, 1)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults[0] == 0) {
        } else {
            finish()
        }
    }

    private fun observeLoadingData() {
        viewModel.isLoading.observe(this) {
            if (it) {
                showLoading()
            } else {
                dismissLoading()
            }
        }
    }

    private fun navigateFragment1() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment, ContactFragment())
            .commit()
    }

//    private fun navigateFragment2(){
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fragment, Deta())
//            .commit()
//    }
    private fun showLoading() {
        loadingDialog.show()
    }

    private fun dismissLoading() {
        loadingDialog.dismiss()
    }

    companion object {
        val PERMISSION =
            listOf(
                "android.permission.READ_CONTACTS",
                "android.permission.WRITE_CONTACTS",
            ).toTypedArray()
    }
}
