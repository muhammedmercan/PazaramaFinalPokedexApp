package com.example.pazaramapokedex.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Other(

    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork?
)