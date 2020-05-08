package com.mohan.reddit.data.model

import com.google.gson.annotations.SerializedName

data class DataMain(
    @SerializedName("data")
    val data: PostDataChildren
)