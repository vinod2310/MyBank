package com.rsasedemo.mybank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random


class DisplayPushActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_push)
        val fromAccount = intent.getStringExtra  (MainActivity.PUSH_MESSAGE_FROMACCOUNT)
        val toAccount = intent.getStringExtra  (MainActivity.PUSH_MESSAGE_TOACCOUNT)
        val amount = intent.getStringExtra  (MainActivity.PUSH_MESSAGE_AMOUNT)
        findViewById<TextView>(R.id.transactionFromValue).also {
            it.setText(fromAccount)
        }

        findViewById<TextView>(R.id.transactionToVal).also {
            it.setText(toAccount)
        }

        findViewById<TextView>(R.id.transactionAmountValue).also {
            it.setText("$"+amount)
        }


    }
    fun doOkay(view:View){
        Log.d("DisplayPushActivity" , "Customer said Okay")
        val fm = FirebaseMessaging.getInstance()
        val map = mapOf("action" to  "__approved__")
        fm.send(
            RemoteMessage.Builder("268728773364@fcm.googleapis.com")
            .setMessageId(Integer.toString(Random.nextInt())).setData(map)
            .build())
        Toast.makeText(baseContext, "Transaction successful", Toast.LENGTH_LONG).show()
        finish()


    }
    fun doNotOkay(view:View){
        Log.d("DisplayPushActivity" , "Customer said Not Okay")
        val fm = FirebaseMessaging.getInstance()
        val map = mapOf("action" to  "__not__approved__")
        fm.send(
            RemoteMessage.Builder("268728773364@fcm.googleapis.com")
                .setMessageId(Integer.toString(Random.nextInt())).setData(map)
                .build())
        Toast.makeText(baseContext, "Transaction denied", Toast.LENGTH_LONG).show()
        finish()
    }

}
