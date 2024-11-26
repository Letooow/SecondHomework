package com.example.secondhomework

data class Response(
    val isGiphy: Boolean,
    val catResponse: CatResponse?,
    val giphyResponse: GiphyResponse?
)
