package com.example.secondhomework

data class GiphyResponse (
    val data: GifData,
    val meta: Meta
)


data class GifData(
    val type: String,
    val id: String,
    val url: String,
    val images: GifImages,
    val title: String
)


data class GifImages(
    val original: GifOriginal
)

data class GifOriginal(
    val url: String,
    val width: String,
    val height: String,
    val size: String
)

data class Meta(
    val status: Int,
    val msg: String
)
