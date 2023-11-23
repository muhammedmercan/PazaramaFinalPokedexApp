package com.example.pokedex.domain.model


import com.example.pazaramapokedex.utils.extractId
import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?

) {

    fun getImageUrl(): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/" +
                "pokemon/other/official-artwork/${url?.extractId()}.png"
    }
}