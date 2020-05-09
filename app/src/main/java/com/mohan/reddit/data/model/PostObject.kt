package com.mohan.reddit.data.model

import com.google.gson.annotations.SerializedName

class PostObject(
    @SerializedName("data")
    val data: PostData
)