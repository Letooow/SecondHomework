package com.example.secondhomework

import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApi {
    @GET("v1/gifs/random")
    suspend fun getRandomGif(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int = 1,
        @Query("tag") tag: String = "funny cat"
    ): GiphyResponse
}
