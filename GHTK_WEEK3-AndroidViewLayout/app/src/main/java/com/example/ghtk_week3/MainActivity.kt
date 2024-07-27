package com.example.ghtk_week3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ghtk_week3.model.Quiz
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private val inputRecyclerView by lazy {
        findViewById<RecyclerView>(R.id.recycler_view)
    }
    private val keyboardList by lazy {
        findViewById<RecyclerView>(R.id.keyboard_letter)
    }
    private val viewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
    }
    private val nextBtn by lazy {
        findViewById<TextView>(R.id.next_text)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initListener()
        observeLivedData()
    }
    private fun initView(){
        initInputBox()
        initKeyboard()
    }

    private fun initListener() {
        nextBtn.setOnClickListener {
            viewModel.getNextQuestion()
        }
    }
    private fun observeLivedData(){
        viewModel.currentQuiz.observe(this){
            if(it!=null){
                viewModel.prepareNextData()
                findViewById<ImageView>(R.id.image_source_quiz).setImageResource(it.imageSource)
                findViewById<ConstraintLayout>(R.id.nextQues).visibility = View.INVISIBLE
                findViewById<TextView>(R.id.textView).visibility = View.INVISIBLE
            }else{
                Toast.makeText(this, "End game", Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    finish()
                },1000)
            }
        }
        viewModel.points.observe(this){
            findViewById<TextView>(R.id.point).text = it.toString()
        }
        viewModel.heartCount.observe(this){
            findViewById<TextView>(R.id.heart_count).text = it.toString()
            if(it == 0){
                Toast.makeText(this, "Game over", Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    finish()
                },1000)
            }
        }
        viewModel.quizStatus.observe(this){
            when(it){
                true -> {
                    findViewById<ConstraintLayout>(R.id.nextQues).visibility = View.VISIBLE
                    findViewById<TextView>(R.id.textView).text = "Bạn trả lời đúng rồi"
                    findViewById<TextView>(R.id.textView).visibility = View.VISIBLE
                }
                false -> {
                    findViewById<TextView>(R.id.textView).visibility = View.VISIBLE
                    findViewById<TextView>(R.id.textView).text = "Bạn trả lời sai rồi"
                }
            }
        }
    }
    // Init the input box, and update the number of box
    private fun initInputBox(){
        inputRecyclerView.apply {
            adapter = viewModel.inputItemAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 7)
        }
    }

    private fun initKeyboard(){
        keyboardList.apply {
            adapter = viewModel.keyboardItemAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 8)
        }
    }
}