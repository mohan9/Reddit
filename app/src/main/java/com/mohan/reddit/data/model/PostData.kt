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
    @SerializedName("thumbnail")
    val thumbnail: String = "",
    @SerializedName("preview")
    val preview: RedditObjectPreview?,
    @SerializedName("over_18")
    val over_18: Boolean,
    @SerializedName("created_utc")
    val created_utc: Double = 0.0,
    @SerializedName("body")
    val body: String = ""
)