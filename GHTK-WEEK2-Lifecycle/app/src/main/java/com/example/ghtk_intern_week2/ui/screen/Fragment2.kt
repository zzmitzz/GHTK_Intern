package com.example.ghtk_intern_week2.ui.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.ghtk_intern_week2.MainViewModel
import com.example.ghtk_intern_week2.R
import com.example.ghtk_intern_week2.base.BaseFragment
import com.example.ghtk_intern_week2.databinding.Fragment1Binding
import com.example.ghtk_intern_week2.databinding.Fragment2Binding
import com.example.ghtk_intern_week2.model.Point
import com.example.ghtk_intern_week2.utils.convertToDouble
import com.example.ghtk_intern_week2.viewmodel.ViewModelFactory

class Fragment2 : BaseFragment<MainViewModel>() {

    private val binding by lazy {
        Fragment2Binding.inflate(layoutInflater)
    }
    private lateinit var point1: Point
    private lateinit var point2: Point
    private lateinit var point3: Point
    private lateinit var checkPoint: Point
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
    override fun initListener() {
        binding.check.setOnClickListener{
            if(checkAllPointsAvailable()){
                viewModel.apply {
                    pointInsideTriangle(checkPoint, point1, point2, point3)
                }
            }
            else{
                Toast.makeText(context, "Please input all points", Toast.LENGTH_SHORT).show()
            }
        }
        binding.navigateBtn.setOnClickListener{
            findNavController().navigate(R.id.action_fragment2_to_fragmentDraw)
        }

    }

    override fun observeLiveData() {

        viewModel.exercise2.observe(this@Fragment2) {
            if(it){
                binding.imageView.setImageResource(R.drawable.yes)
            }else{
                binding.imageView.setImageResource(R.drawable.no)
            }
        }
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactory())[MainViewModel::class.java]
    }
    private fun checkAllPointsAvailable(): Boolean{
        binding.run {
            val inputPoints = listOf(px1, py1, px2, py2, px3, py3, px4, py4)
            val points = inputPoints.mapNotNull { it.text.convertToDouble() }
            if (points.size == 8) {
                this@Fragment2.apply {
                    point1 = Point(points[0], points[1])
                    point2 = Point(points[2], points[3])
                    point3 = Point(points[4], points[5])
                    checkPoint = Point(points[6], points[7])
                }
                return true
            }
            return false
        }
    }

}