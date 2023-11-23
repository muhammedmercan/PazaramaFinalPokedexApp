package com.example.pazaramapokedex.domain.model


import com.google.gson.annotations.SerializedName

data class TypeX(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)