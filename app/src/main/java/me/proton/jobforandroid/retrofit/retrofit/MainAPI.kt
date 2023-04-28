package me.proton.jobforandroid.retrofit.retrofit

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MainAPI {
    @GET("auth/products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Product

    @POST("auth/login")
    suspend fun auth(@Body request: AuthRequest): User

    @GET("auth/products")
    suspend fun getAllProducts(): Products

    @Headers("Content-Type: application/json")
    @GET("auth/products/search")
    suspend fun getProductByNameAuth(@Header("Authorization") token: String,  @Query("q") name: String): Products


    @GET("auth/products/search")
    suspend fun getProductByName(@Query("q") name: String): Products
}