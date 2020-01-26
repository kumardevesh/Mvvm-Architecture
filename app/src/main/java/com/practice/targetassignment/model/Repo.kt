package com.practice.targetassignment.model

import com.google.gson.annotations.SerializedName

data class Repo(
    @SerializedName("author")
    val author: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("avatar")
    val avatar: String? = null,
    @SerializedName("url")
    val url: String,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("stars")
    val stars: Int = 0,
    @SerializedName("forks")
    val forks: Int = 0,
    @SerializedName("language")
    val language: String? = null,
    var isExpandedState: Boolean = false
)


