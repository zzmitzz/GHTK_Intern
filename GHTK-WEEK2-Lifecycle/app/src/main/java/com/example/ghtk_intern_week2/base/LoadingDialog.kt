package com.example.ghtk_intern_week2.base

import android.app.Dialog
import android.content.Context
import android.os.Handler
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.example.ghtk_intern_week2.R

class LoadingDialog(private val context: Context) {
    var dialog: Dialog = Dialog(context)
    lateinit var handler: Handler
    lateinit var runnable: Runnable
    init {

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.loading_dialog)
        dialog.setCancelable(false)
        val window = dialog.window
        window!!.setBackgroundDrawableResource(android.R.color.transparent)
        window.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
        )
        val layoutParams = window.attributes
        layoutParams.gravity = Gravity.CENTER
        window.attributes = layoutParams

        handler = Handler()
        runnable = Runnable {
            if(dialog.isShowing){
                dialog.hide()
            }
        }
    }
    fun showDialog(){
        dialog.show()
        runnable.let { handler.postDelayed(it, 90000) }
    }
    fun dismissDialog(){
        dialog.dismiss()
        try {
            runnable.let { handler.removeCallbacks(it) }
        } catch (_: Exception) {
            Toast.makeText(context, "Some thing went wrong, please reset the application", Toast.LENGTH_SHORT).show()
        }
    }
}