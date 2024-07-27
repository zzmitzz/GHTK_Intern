package com.example.ghtk_week3.manager

import android.util.Log
import com.example.ghtk_week3.R
import com.example.ghtk_week3.model.Quiz
import java.util.Locale

object GameManager {
    val data: MutableList<Quiz> = mutableListOf()
    /*
    Fake list store the index of this character that place on the keyboard for restoring in the future
    when the user click to undo the character.
    */
    var fakeList: MutableList<Int> = mutableListOf()
    var currentQuiz: Quiz? = null
    init {
        // Manually import data
        addQuestion()
        initFakeList()
    }

    // Handle if number of question is all checked before
    @Synchronized
    fun getNextQuestion(): Quiz?{
        var quiz = data.random()
        var count = 0
        while (quiz.checked){
            count+=1
            quiz = data.random()
            if(count == data.size) {
                return null
            }
        }
        quiz.checked = true
        currentQuiz = quiz
        return quiz
    }
    private fun initFakeList(){
        fakeList.clear()
        repeat(data.size){
            fakeList.add(-1)
        }
    }
    fun checkAnswer(answer: String): Boolean {
        Log.d("TAG", answer.toLowerCase(Locale.ROOT) + currentQuiz?.stringCorresponding!! )
        return answer.toLowerCase(Locale.ROOT) == currentQuiz?.stringCorresponding
    }
    private fun addQuestion(){
        data.add(Quiz(R.drawable.aomua, "aomua", false))
        data.add(Quiz(R.drawable.khoailang, "khoailang", false))
        data.add(Quiz(R.drawable.tohoai, "tohoai", false))
        data.add(Quiz(R.drawable.baocao, "baocao", false))
        data.add(Quiz(R.drawable.kiemchuyen, "kiemchuyen", false))
        data.add(Quiz(R.drawable.totien, "totien", false))
        data.add(Quiz(R.drawable.canthiep, "canthiep", false))
        data.add(Quiz(R.drawable.lancan, "lancan", false))
        data.add(Quiz(R.drawable.tranhthu, "tranhthu", false))
        data.add(Quiz(R.drawable.cattuong, "cattuong", false))
        data.add(Quiz(R.drawable.masat, "masat", false))
        data.add(Quiz(R.drawable.vuaphaluoi, "vuaphaluoi", false))
        data.add(Quiz(R.drawable.chieutre, "chieutre", false))
        data.add(Quiz(R.drawable.nambancau, "nambancau", false))
        data.add(Quiz(R.drawable.vuonbachthu, "vuonbachthu", false))
        data.add(Quiz(R.drawable.danhlua, "danhlua", false))
        data.add(Quiz(R.drawable.oto, "oto", false))
        data.add(Quiz(R.drawable.xakep, "xakep", false))
        data.add(Quiz(R.drawable.danong, "danong", false))
        data.add(Quiz(R.drawable.quyhang, "quyhang", false))
        data.add(Quiz(R.drawable.xaphong, "xaphong", false))
        data.add(Quiz(R.drawable.giandiep, "giandiep", false))
        data.add(Quiz(R.drawable.songsong, "songsong", false))
        data.add(Quiz(R.drawable.xedapdien, "xedapdien", false))
        data.add(Quiz(R.drawable.giangmai, "giangmai", false))
        data.add(Quiz(R.drawable.thattinh, "thattinh", false))
        data.add(Quiz(R.drawable.hoidong, "hoidong", false))
        data.add(Quiz(R.drawable.thothe, "thothe", false))
        data.add(Quiz(R.drawable.hongtam, "hongtam", false))
        data.add(Quiz(R.drawable.tichphan, "tichphan", false))
        initFakeList()
    }
}