package com.smartherd.myapp.activity

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.smartherd.myapp.helper.BitmapHelper
import com.smartherd.myapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val REQ = 1
    private var bitMap: Bitmap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        addImage.setOnClickListener {
            openGallery()
        }


        nextBtn.setOnClickListener {

            val articleName = articlename.editableText.toString()
            val userName = username.editableText.toString()

            if (articleName.isEmpty()){
                articlename.error = "Please Enter Article Name"
            }else if (userName.isEmpty()){
                username.error = "Please Enter User Name"
            }else if (bitMap == null){
                Toast.makeText(this, "Please Select a Image", Toast.LENGTH_SHORT).show()
            }else{
                BitmapHelper.getInstance().setBitmap(bitMap!!)
                val intent = Intent(this, PostActivity::class.java)
                intent.putExtra(PostActivity.ARTICALNAME, articleName)
                intent.putExtra(PostActivity.USERNAME,userName)
                startActivity(intent)
            }
        }
    }

    private fun openGallery() {
        val pickImage = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickImage, REQ)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ && resultCode == RESULT_OK) {
            val uri = data?.data
            bitMap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            imageView.setImageBitmap(bitMap)
        }
    }
}