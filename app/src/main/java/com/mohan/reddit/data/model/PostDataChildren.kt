package com.mohan.reddit.data.model

import com.google.gson.annotations.SerializedName

data class PostDataChildren(
    @SerializedName("children")
    val children: ArrayList<PostObject>
)