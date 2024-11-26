package com.example.secondhomework

import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApi {
    @GET("v1/gifs/random")
    suspend fun getRandomGif(
        @Query("api_key") apiKey: String,
        @Query("tag") tag: String = "cat",
        @Query("limit") limit: Int = 1
    ): GiphyResponse
}
