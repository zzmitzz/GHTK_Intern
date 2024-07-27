package com.example.ghtk_intern_week2.utils

import android.text.Editable
import android.util.Log
import com.example.ghtk_intern_week2.model.Point
import kotlin.math.pow
import kotlin.math.sqrt


fun Point.distance(b: Point) = sqrt((b.x!! - x!!).pow(2) + (b.y!! - y!! ).pow(2))

fun areaCalculate(a: Point, b: Point, c: Point) : Double{
    // Heron's formula
    val edge1 = a.distance(b)
    Log.d("TAG", "areaCalculate: $edge1")
    val edge2 = b.distance(c)
    val edge3 = c.distance(a)
    val p = (edge1 + edge2 + edge3)/2
    return sqrt(p*(p-edge1)*(p-edge2)*(p-edge3))
}

fun Editable.convertToDouble() : Double? {
    if(this.toString().isNotEmpty()){
        return this.toString().toDouble()
    }
    return null
}