package com.example.ghtk_intern_week2.view

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.location.GnssAntennaInfo.Listener
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet.Motion
import com.example.ghtk_intern_week2.MainActivity
import com.example.ghtk_intern_week2.base.BaseFragment
import com.example.ghtk_intern_week2.model.Point
import com.example.ghtk_intern_week2.ui.screen.draw_screen.DrawTriangleFragment


interface checkPointListener {
    fun checkPoint(point: List<Point>, cPoint : Point)
}

class TriangleCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {


    private val paint = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        style = Paint.Style.FILL
        strokeWidth = 5f
    }
    private val points: MutableList<Point> = mutableListOf()
    private val scaleFactor = 2.0f // Scaling factor to make dots larger
    private val dotRadius = 5f * scaleFactor
    private var checkPoint : Point? = null
    private var listener : checkPointListener? = null

    fun setListener(listener : checkPointListener){
        this.listener = listener
        Log.d("CustomView", "setListener: ${this.listener.hashCode()}")
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.d("CustomView", "onMeasure: $widthMeasureSpec, $heightMeasureSpec")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        Log.d("CustomView", "onLayout: $left, $top, $right, $bottom")
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (point in points) {
            canvas.drawCircle(point.x!!.toFloat(), point.y!!.toFloat(), dotRadius, paint)
        }
        if(checkPoint != null){
            canvas.drawCircle(checkPoint!!.x!!.toFloat(), checkPoint!!.y!!.toFloat(), dotRadius, paint)

        }
        drawLine(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event!!.action){
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                val x = event.x
                val y = event.y
                if(points.size < 3){
                    points.add(Point(x.toDouble(),y.toDouble()))
                    performClick()
                }else{
                    checkPoint = Point(x.toDouble(),y.toDouble())
                    performClick()
                    listener?.checkPoint(points, checkPoint!!)
                }
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        invalidate()
        return super.performClick()
    }
    fun clearScreen() {
        points.clear()
        checkPoint = null
        invalidate()
    }
    fun drawLine(canvas: Canvas){
        for(p1 in points){
            for(p2 in points){
                if(p1 != p2){
                   canvas.drawLine(p1.x!!.toFloat(), p1.y!!.toFloat(), p2.x!!.toFloat(), p2.y!!.toFloat(), paint)
                }
            }
        }
    }

}