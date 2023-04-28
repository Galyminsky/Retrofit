package me.proton.jobforandroid.retrofit.retrofit

import retrofit2.http.GET
import retrofit2.http.Path

interface PostApi {
    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id : Int) : Post
}