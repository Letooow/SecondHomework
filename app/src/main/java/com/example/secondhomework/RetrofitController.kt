package com.example.secondhomework

import retrofit2.Retrofit
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitController {
    private const val BASE_URL = "https://api.thecatapi.com/"


    val api: CatApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatApi::class.java)
    }

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("x-api-key", "live_No9HILaUmdZOe8ehkG8d3ALkTQIxzAEvRtH4CXf1CiFhfUiIYKlOCmb3s9Cxizg4")
                    .build()
                chain.proceed(request)
            }
            .build()
    }

}