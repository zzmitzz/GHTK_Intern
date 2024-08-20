package com.example.applicationmemorycpuleak

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.applicationmemorycpuleak.databinding.ActivityMemoryLeakBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.sqrt

class MemoryLeakActivity : AppCompatActivity() {

    private var binding: ActivityMemoryLeakBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_leak)
        findViewById<TextView>(R.id.back).setOnClickListener{
            finish()
        }
        // Cause memory leak since the singleton already exists
        Singleton.getContext(this)
        findViewById<Button>(R.id.button2).setOnClickListener{
            val a = InnerClassLeak()
            a.leak()
        }
        binding = ActivityMemoryLeakBinding.inflate(layoutInflater)
        viewModel.makeMemoryLeak()
        findViewById<Button>(R.id.button3).setOnClickListener{
            CPULEAK()
        }
    }
    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    override fun onDestroy(){
        super.onDestroy()
        /* If the binding is not destroy can cause memory leak */
//        binding = null
    }
    fun CPULEAK(){
        CoroutineScope(Dispatchers.Default).launch {
            var a: Double = 2.0
            repeat(5){
                launch {
                    while(true){
                        a = sqrt(a)
                        Log.d("CPULEAK","$a")
                    }
                }
            }
        }
    }
    inner class InnerClassLeak(){
        fun leak(){
            Handler(Looper.getMainLooper()).postDelayed({
                Toast.makeText(applicationContext,"Even the activity is destroyed but the message still alive in the heap",Toast.LENGTH_SHORT).show()
            },5000)
        }
    }
}