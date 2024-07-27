package com.example.ghtk_week3.manager

object FilledBoxManager {
    var listFilledBox: MutableList<Char> = mutableListOf()
    fun findFirstEmptyChar(): Int{
        for(i in 0..<listFilledBox.size){
            if(listFilledBox[i] == ' '){
                return i
            }
        }
        return listFilledBox.size
    }
}