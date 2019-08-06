package com.rsasedemo.mybank


import android.app.*
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

import android.util.Log
import android.widget.EditText
import com.rsasedemo.mybank.MainActivity.Companion.PUSH_MESSAGE
import com.rsasedemo.mybank.MainActivity.Companion.PUSH_MESSAGE_AMOUNT
import com.rsasedemo.mybank.MainActivity.Companion.PUSH_MESSAGE_FROMACCOUNT
import com.rsasedemo.mybank.MainActivity.Companion.PUSH_MESSAGE_TOACCOUNT
import java.io.DataOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

/**
 * Created by What's That Lambda on 11/6/17.
 */

class MessageReceiver : FirebaseMessagingService() {
    var TAG = "MessageReceiver"

    override fun onNewToken(p0: String?) {
        super.onNewToken(p0)
        Log.d("Token:", p0);
        sendToServer(p0)
    }

    fun sendToServer(token: String?) {

        try {
            val url = URL(getString(R.string.storeTokenUrl))
            val connection = url.openConnection() as HttpURLConnection

            connection.doOutput = true
            connection.doInput = true
            connection.requestMethod = "POST"
            val dos = DataOutputStream(connection.outputStream)

            dos.writeBytes("toeknValue=" + token!!)

            connection.connect()

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                // Do whatever you want after the
                // token is successfully stored on the server
            }

        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)

        val title = remoteMessage!!.data["title"]
        val message = remoteMessage.data["body"]
        Log.d("Received Notification","Received Notification"+remoteMessage?.data);


        remoteMessage?.data?.isNotEmpty()?.let {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
            MainActivity.pushData["active"] = true
            MainActivity.pushData["fromAccount"] = remoteMessage?.data["fromAccount"] as String
            MainActivity.pushData["toAccount"] = remoteMessage?.data["toAccount"]as String
            MainActivity.pushData["amount"] = remoteMessage?.data["amount"]as String
            showNotifications(title, message);
            showPush(MainActivity.pushData["fromAccount"] as String, MainActivity.pushData["toAccount"] as String,
                    MainActivity.pushData["amount"]as String)
        }

        // Check if message contains a notification payload.
        remoteMessage?.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }

    }

    private fun showPush(fromAccount:String, toAccount:String, amount:String){

        val intent = Intent(this, DisplayPushActivity::class.java).apply {
            putExtra(PUSH_MESSAGE_FROMACCOUNT, fromAccount)
            putExtra(PUSH_MESSAGE_TOACCOUNT, toAccount)
            putExtra(PUSH_MESSAGE_AMOUNT, amount)
        }
        startActivity(intent)
    }



    private fun showNotifications(title: String?, msg: String?) {
        val i = Intent(this, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            this, REQUEST_CODE,
            i, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(this)
            .setContentText(msg)
            .setContentTitle(title)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .build()

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        private val REQUEST_CODE = 1
        private val NOTIFICATION_ID = 6578
    }
}
