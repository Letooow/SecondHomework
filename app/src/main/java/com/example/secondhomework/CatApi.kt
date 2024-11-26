package com.example.secondhomework

import retrofit2.http.GET
import retrofit2.http.Query
import kotlin.collections.emptyList

interface CatApi {
    @GET("v1/images/search")
    suspend fun getRandomCat(
        @Query("limit") limit: Int = 1,

    ): List<CatResponse>
}

