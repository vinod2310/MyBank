package com.rsasedemo.mybank

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.InputType
import android.util.Log
import android.widget.EditText
import android.widget.ProgressBar

class OneTimePasswordDisplay : AppCompatActivity() {
    var mProgressBar: ProgressBar? = null;
    var count = 0;

    val mCountDownTimer  =  object: CountDownTimer(20000, 100) {
        override fun onTick(millisUntilFinished: Long) {
            count++;
            if (count%2 != 0) return
            mProgressBar!!.setProgress(mProgressBar!!.progress.minus(1), true);

        }

        override fun onFinish() {
            Log.d("Starting again...........", "sss")
            val randomDouble = Math.random()
            mProgressBar!!.setProgress(0);
            mProgressBar!!.setProgress(mProgressBar!!.max);

            (findViewById (R.id.otpField) as EditText).setText((randomDouble*1000000L).toInt().toString())

            start()

        }
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_time_password_display)
        val otpValue = findViewById (R.id.otpField) as EditText
        val randomDouble = Math.random()
        val otpFiled = findViewById (R.id.otpField) as EditText
        otpFiled.setInputType(InputType.TYPE_NULL)
        otpFiled.setText((randomDouble*1000000L).toInt().toString())
        mProgressBar = findViewById (R.id.progressbar) as ProgressBar;
        mProgressBar!!.setProgressTintList(ColorStateList.valueOf(Color.RED));
        mProgressBar!!.setRotation(0.0F)
//        mProgressBar.setProgress(mProgressBar.max, true);

        mCountDownTimer.start()

    }


//    fun CountDown() {
//        var i=0;
//
//        val timer = object: CountDownTimer {
//            override fun onTick(millisUntilFinished: Long) {...}
//
//            override fun onFinish() {...}
//        }
//        timer.start()
//
//        mProgressBar = (ProgressBar) findViewById (R.id.progressbar);
//        mProgressBar.setProgress(i);
//        mCountDownTimer = CountDownTimer (5000, 1000) {
//
//            override fun onTick(long millisUntilFinished) {
//                Log.v("Log_tag", "Tick of Progress" + i + millisUntilFinished);
//                i++;
//                mProgressBar.setProgress((int) i *100 / (5000 / 1000));
//
//            }
//
//            @Override
//            public void onFinish() {
//                //Do what you want
//                i++;
//                mProgressBar.setProgress(100);
//            }
//        };
//        mCountDownTimer.start();
//    }
}
