package com.example.ghtk_intern_week2.ui.screen.draw_screen

import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.ghtk_intern_week2.MainViewModel
import com.example.ghtk_intern_week2.R
import com.example.ghtk_intern_week2.base.BaseFragment
import com.example.ghtk_intern_week2.model.Point
import com.example.ghtk_intern_week2.view.TriangleCustomView
import com.example.ghtk_intern_week2.view.checkPointListener

// Miracle, the view binding of this page not working for some reason, so findViewById is the dummy choice damn.
class DrawTriangleFragment : BaseFragment<MainViewModel>(), checkPointListener {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_triangle_drawing, container, false)
    }

    override fun initView() {
        view?.findViewById<TriangleCustomView>(R.id.custom_view)?.setListener(this)
        super.initView()
    }
    override fun initListener() {
        view?.findViewById<ImageView>(R.id.backBtn)?.setOnClickListener{
            findNavController().navigate(R.id.action_fragmentDraw_to_fragment2)
        }
        view?.findViewById<Button>(R.id.clearBtn)?.setOnClickListener{
            view?.findViewById<TriangleCustomView>(R.id.custom_view)?.clearScreen()
        }
    }

    override fun observeLiveData() {
        viewModel.exercise2.observe(this@DrawTriangleFragment) {
            if(it){
                Log.d("CustomView", "modify: $it")
                view?.findViewById<TextView>(R.id.textView2)?.apply {
                    setText(R.string.accept)
                    setTextColor(Color.GREEN)
                }
            }else{
                view?.findViewById<TextView>(R.id.textView2)?.apply {
                    setText(R.string.reject)
                    setTextColor(Color.RED)
                }

            }
        }
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this@DrawTriangleFragment)[MainViewModel::class.java]
    }


    override fun checkPoint(point: List<Point>, cpoint: Point) {
        viewModel.apply {
            Log.d("CustomView", "checkPoint: $point, $cpoint")
            pointInsideTriangle(cpoint, point[0], point[1], point[2])

        }
    }
}