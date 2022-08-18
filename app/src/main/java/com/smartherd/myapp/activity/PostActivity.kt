package com.smartherd.myapp.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.smartherd.myapp.*
import com.smartherd.myapp.api.AddPostResponse
import com.smartherd.myapp.api.ApiInterface
import com.smartherd.myapp.api.ServiceBuilder
import com.smartherd.myapp.helper.BitmapHelper
import kotlinx.android.synthetic.main.activity_post.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostActivity : AppCompatActivity() {


    var iscolore = false
    companion object {
        const val ARTICALNAME = "articlename"
        const val USERNAME = "username"
        const val POSTIMAGE = "postimage"
    }

    val channelId = "channel-01";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)


        //get article name and username from intent
        val articlename = intent.getStringExtra(ARTICALNAME)
        val username = intent.getStringExtra(USERNAME)


        //set data into activity_post.xml
        userName1.text = username
        articleName1.text = articlename
        image1.setImageBitmap(BitmapHelper.getInstance().getBitmap())


        //Click Handling on informative button
        informative.setOnClickListener {
            if (iscolore==false){
                informative.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_menu_book_24red,0,0,0)
                informative.setTextColor(Color.parseColor("#F10606"))
                iscolore=true
                callAPI(articlename,username)
            }else if(iscolore==true){
                informative.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_menu_book_24,0,0,0)
                informative.setTextColor(Color.parseColor("#FF000000"))
                iscolore=false
            }
        }

    }


    //function for API Calling
    private fun callAPI(article : String? , user : String?) {

        val destinationService = ServiceBuilder.buildService(ApiInterface::class.java)
        val requestCall = destinationService.addPost("1","article","202","23","1019","user")

        requestCall.enqueue(object : Callback<AddPostResponse>{

            override fun onResponse(call: Call<AddPostResponse>, response: Response<AddPostResponse>) {
                try {
                    if (response.body()!!.status.equals("1")) {
                        rtNotification()
                    }else{
                        Toast.makeText(this@PostActivity, "API Not Hit Successfully", Toast.LENGTH_SHORT).show()
                    }
                } catch (e : Exception){
                    Log.e("failure",e.localizedMessage)
                }

            }

            override fun onFailure(call: Call<AddPostResponse>, t: Throwable) {
                Log.e("exp",t.localizedMessage)
                Toast.makeText(this@PostActivity, "Failed to add item", Toast.LENGTH_SHORT).show()
            }

        })

    }

    //notificatoin function when API HIT successfully
    private fun rtNotification() {

        val notificationBuilder = NotificationCompat.Builder(this,channelId)
            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
            .setContentTitle("My App")
            .setColor(ContextCompat.getColor(this, R.color.black))
            .setContentText("API HIT Successfully");

        val notificationManager :  NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelId,"My Notification",NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(1,notificationBuilder.build());
    }



}