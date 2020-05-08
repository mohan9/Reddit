package com.mohan.reddit.data.model

import com.google.gson.annotations.SerializedName

data class PostData(
    @SerializedName("title")
    val title: String = "",
    @SerializedName("author")
    val author: String = "",
    @SerializedName("permalink")
    val permalink: String = "",
    @SerializedName("url")
    val url: String = "",
    @SerializedName("created_utc")
    val created_utc: Double = 0.0
)