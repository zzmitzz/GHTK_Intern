package com.example.ghtk_intern_week2

import android.icu.number.IntegerWidth
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghtk_intern_week2.base.BaseViewModel
import com.example.ghtk_intern_week2.model.Point
import com.example.ghtk_intern_week2.utils.areaCalculate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt


class MainViewModel : BaseViewModel() {
    val exercise2: MutableLiveData<Boolean> = MutableLiveData()
    private val freq: MutableMap<Char, Int> by lazy {
        mutableMapOf()
    }

    // LiveData, callback ?
    fun characterCountFrequency(a: String, callback : (MutableMap<Char, Int>) -> Unit ){
        freq.clear()
        viewModelScope.launch {
            showLoadingDialog()
            for(c in a.toCharArray()){
                if(freq[c] != null){
                    freq[c] = freq[c]!! + 1
                }else{
                    freq[c] = 1
                }
            }
            hideLoadingDialog()
            callback(freq)
        }
    }

    fun pointInsideTriangle(checkpoint: Point, a: Point, b: Point, c: Point){
        viewModelScope.launch {
            showLoadingDialog()
            val sumArea = areaCalculate(checkpoint, a,b) + areaCalculate(checkpoint, a,c) + areaCalculate(checkpoint,b,c)
            exercise2.postValue((sumArea*100).roundToInt() == (areaCalculate(a,b,c)*100).roundToInt())
            hideLoadingDialog()
        }
    }

}