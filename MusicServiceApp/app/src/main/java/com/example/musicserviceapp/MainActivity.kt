package com.example.musicserviceapp

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.musicserviceapp.databinding.ActivityMainBinding
import com.example.musicserviceapp.ui.bottom_music_fragment.BottomMusicFragment
import com.example.musicserviceapp.ui.homepage.view.HomePageFragment

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment1, HomePageFragment())
            .commit()
        binding.fragment2.visibility = View.GONE
        initObserve()
        initListener()
    }
    private fun initObserve(){
        viewModel.getMusic().observe(this){
            if(it != null){
                binding.fragment2.visibility = View.VISIBLE
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment2, BottomMusicFragment())
                    .commit()
            }else{
                binding.fragment2.visibility = View.GONE
            }
        }
    }
    private fun initListener(){

    }

}