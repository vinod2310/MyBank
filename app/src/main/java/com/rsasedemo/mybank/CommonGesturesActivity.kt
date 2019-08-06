package com.rsasedemo.mybank


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.widget.TextView
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity


class CommonGesturesActivity(mainActivityParam: AppCompatActivity?) : AppCompatActivity(),
    GestureDetector.OnGestureListener {
    val mainActivity = mainActivityParam;
    override fun onLongPress(p0: MotionEvent?) {
        return ;
    }


    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return true;
    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {

//        val swipeEvent  = Intent(this@Main,com.rsasedemo.mybank.MainActivity::class.java )
//        startActivity(swipeEvent, null)
        return false;
    }

    override fun onDown(p0: MotionEvent?): Boolean {
        return true;
    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        return true;
    }

    override fun onShowPress(p0: MotionEvent?) {
        return ;
    }
}
