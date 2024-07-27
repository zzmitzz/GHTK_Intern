package com.example.ghtk_week3.manager

object KeyboardInputManager {
    var listInput = mutableListOf<Char>()

    fun initListInput(answer: String): MutableList<Char>{
        listInput.clear()
        listInput = answer.toCharArray().toMutableList()
        listInput = listInput.map {
            it.uppercaseChar()
        }.toMutableList()
        while(listInput.size < 16){
            listInput.add(('A'..'Z').random())
        }
        listInput.shuffle()
        return listInput
    }
}