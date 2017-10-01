package com.poad1010.example.helloandroid.model

import com.google.gson.annotations.SerializedName

class Tag(
    @SerializedName("followers")
    val followers: Int,
    @SerializedName("icon_urls")
    val icon_urls: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("items_count")
    val items_count: Int
)