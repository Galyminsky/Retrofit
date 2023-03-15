package me.proton.jobforandroid.retrofit.retrofit

import retrofit2.http.GET
import retrofit2.http.Path

interface ProductAPI {
    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Product
}