package com.smartherd.myapp.api

import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

        @FormUrlEncoded
        @POST("react.json")
        fun addPost(@Field("user_id") user_id : String,
                           @Field("type") type : String,
                           @Field("content_id") content_id : String,
                           @Field("reaction") reaction : String,
                           @Field("comment_id") comment_id : String,
                           @Field("user_type") user_type : String ): Call<AddPostResponse>


}