package com.example.pazaramapokedex.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Stat(
    @SerializedName("base_stat")
    val baseStat: Int?,
    @SerializedName("stat")
    val stat: StatX?
)