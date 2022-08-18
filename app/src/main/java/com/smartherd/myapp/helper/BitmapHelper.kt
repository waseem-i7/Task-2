package com.smartherd.myapp.helper

import android.graphics.Bitmap

class BitmapHelper {

    private var bitmap : Bitmap? = null
    companion object {
        private val instance : BitmapHelper = BitmapHelper()

        fun getInstance() : BitmapHelper {
            return instance
        }
    }


    fun getBitmap() : Bitmap? {
        return bitmap
    }

    fun setBitmap(bitmap: Bitmap){
        this.bitmap = bitmap
    }

}