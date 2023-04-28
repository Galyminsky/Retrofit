package me.proton.jobforandroid.retrofit.retrofit

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MainAPI {
    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Product

    @POST("auth/login")
    suspend fun auth(@Body request: AuthRequest): User

    @GET("products")
    suspend fun getAllProducts(): Products
}