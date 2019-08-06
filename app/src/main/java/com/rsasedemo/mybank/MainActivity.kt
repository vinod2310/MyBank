package com.rsasedemo.mybank

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import java.io.DataOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import kotlin.random.Random


private const val DEBUG_TAG = "Gestures"

class MainActivity :
    Activity(),
    GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener {
    companion object {
        var pushData = mutableMapOf<Any,Any>(
            "active" to false, "fromAccount" to -1,
            "toAccount" to -1,
            "whois" to "__NONE__"

        )
        var PUSH_MESSAGE="PUSH_MESSAGE" as String
        var PUSH_MESSAGE_FROMACCOUNT="PUSH_FROMACCOUNT" as String
        var PUSH_MESSAGE_TOACCOUNT="PUSH_TOACCOUONT" as String
        var PUSH_MESSAGE_AMOUNT="PUSH_AMOUNT" as String


    }
    val TAG = "BNELAB"
    val CHANNEL_ID="AuthChanel"

    private lateinit var mDetector: GestureDetectorCompat

    fun sendToServer(token: String?) {

            val fm = FirebaseMessaging.getInstance()
            val map = mapOf("token" to  token)
            fm.send(
                RemoteMessage.Builder("268728773364@fcm.googleapis.com")
                    .setMessageId(Integer.toString(Random.nextInt())).setData(map)
                    .build())

    }

    private fun showPush(pushData : MutableMap<Any,Any>){
//        val editText = findViewById<EditText>(R.id.transactionRecord)
//        val message = editText.text.toString()
        val intent = Intent(this, DisplayPushActivity::class.java).apply {
            putExtra(PUSH_MESSAGE_FROMACCOUNT, pushData.get("fromAccount") as String)
            putExtra(PUSH_MESSAGE_TOACCOUNT, pushData.get("toAccount") as String)
            putExtra(PUSH_MESSAGE_AMOUNT, pushData.get("amount") as String)
        }
        startActivity(intent)
    }

    // Called when the activity is first created.
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Instantiate the gesture detector with the
        // application context and an implementation of
        // GestureDetector.OnGestureListener
        mDetector = GestureDetectorCompat(this, this)
        // Set the gesture detector as the double tap
        // listener.
        mDetector.setOnDoubleTapListener(this)

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast
                val msg = getString(R.string.msg_token_fmt, token)
                try {
                    sendToServer(token)
                }catch (e :Exception ){
                    e.printStackTrace()
                }
                Log.d(TAG, msg)
                //Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            })

        if(pushData["active"]as Boolean){
            Toast.makeText(baseContext, pushData.toString(), Toast.LENGTH_LONG).show()
            pushData["active"] = false;
            showPush(pushData);

        }else{
            Toast.makeText(baseContext, "No Push", Toast.LENGTH_LONG).show()

        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = descriptionText
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }


    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (mDetector.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    override fun onDown(event: MotionEvent): Boolean {
        Log.d(DEBUG_TAG, "onDown: $event")
        return true
    }

    override fun onFling(
        event1: MotionEvent,
        event2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        Log.d(DEBUG_TAG, "onFling: $event1 $event2")
        val swipeEvent  = Intent(this,com.rsasedemo.mybank.OneTimePasswordDisplay::class.java )
        startActivity(swipeEvent, null)
        return false
    }

    override fun onLongPress(event: MotionEvent) {
        Log.d(DEBUG_TAG, "onLongPress: $event")
    }

    override fun onScroll(
        event1: MotionEvent,
        event2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        Log.d(DEBUG_TAG, "onScroll: $event1 $event2")
        return true
    }

    override fun onShowPress(event: MotionEvent) {
        Log.d(DEBUG_TAG, "onShowPress: $event")
    }

    override fun onSingleTapUp(event: MotionEvent): Boolean {
        Log.d(DEBUG_TAG, "onSingleTapUp: $event")
        return true
    }

    override fun onDoubleTap(event: MotionEvent): Boolean {
        Log.d(DEBUG_TAG, "onDoubleTap: $event")
        return true
    }

    override fun onDoubleTapEvent(event: MotionEvent): Boolean {
        Log.d(DEBUG_TAG, "onDoubleTapEvent: $event")
        return true
    }

    override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
        Log.d(DEBUG_TAG, "onSingleTapConfirmed: $event")
        return true
    }

}
