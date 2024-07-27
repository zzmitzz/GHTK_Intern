package com.example.ghtk_week3

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ghtk_week3.manager.FilledBoxManager
import com.example.ghtk_week3.manager.GameManager
import com.example.ghtk_week3.manager.KeyboardInputManager
import com.example.ghtk_week3.model.Quiz
import com.example.ghtk_week3.ui.adapter.KeyboardItemAdapter
import com.example.ghtk_week3.ui.adapter.QuizItemAdapter
import kotlinx.coroutines.launch
import java.security.Key

class MainViewModel : ViewModel() {
    val inputItemAdapter by lazy {
        QuizItemAdapter()
    }
    val keyboardItemAdapter by lazy {
        KeyboardItemAdapter()
    }
    var heartCount: MutableLiveData<Int> = MutableLiveData(5)
    var points : MutableLiveData<Int> = MutableLiveData(0)
    var currentQuiz: MutableLiveData<Quiz?> = MutableLiveData()

    var quizStatus: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    init {
        getNextQuestion()
    }
    fun prepareNextData(){
        viewModelScope.launch {
//            showDialogLoading()
            updateNumberOfInputBox()
            updateNumberOfKeyboardBox()
//            hideDialogLoading()
        }
    }

    fun getNextQuestion(){
        currentQuiz.value = nextQuestion()
    }
    private fun updateNumberOfInputBox(){
        FilledBoxManager.listFilledBox.clear()
        repeat(currentQuiz.value!!.stringCorresponding.length){
            FilledBoxManager.listFilledBox.add(' ')
        }
        inputItemAdapter.setListData(FilledBoxManager.listFilledBox)
        // Handle delete the char
        inputItemAdapter.invoke = {
            inputItemAdapter.updateItem(' ',it)
            keyboardItemAdapter.updateItem(GameManager.fakeList[it], "update")
        }
    }
    private fun updateNumberOfKeyboardBox(){
        keyboardItemAdapter.setData(getCharacters(currentQuiz.value!!.stringCorresponding))
        keyboardItemAdapter.invoke = {
            val positionUpdate = FilledBoxManager.findFirstEmptyChar()
            if(positionUpdate < FilledBoxManager.listFilledBox.size){
                GameManager.fakeList[positionUpdate] = it
                inputItemAdapter.updateItem(KeyboardInputManager.listInput[it], positionUpdate)
            }
            if(positionUpdate == FilledBoxManager.listFilledBox.size - 1){
                // Check answer
                checkAnswer(inputItemAdapter.data.joinToString(""))
            }
        }
    }
    private fun nextQuestion(): Quiz? = GameManager.getNextQuestion()
    private fun getCharacters(answer: String): MutableList<Char> = KeyboardInputManager.initListInput(answer)

    private fun checkAnswer(answer: String){
        if(GameManager.checkAnswer(answer)){
            points.value = points.value?.plus(100)
            quizStatus.postValue(true)
        }else{
            heartCount.value = heartCount.value?.minus(1)
            quizStatus.postValue(false)
        }
    }
}