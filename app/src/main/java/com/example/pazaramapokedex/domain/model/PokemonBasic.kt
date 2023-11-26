package com.example.pokedex.domain.model


import com.example.pazaramapokedex.utils.extractId
import com.google.gson.annotations.SerializedName

data class PokemonBasic(

    val id: Int,

    @SerializedName("count")
    val count: Int? = 0,
    @SerializedName("next")
    val next: String? = "",
    @SerializedName("previous")
    val previous: Any? = "",


    //@SerializedName("results")
    //val results: List<Result?>?,

    val name: String?,
    val url: String?
) {

    fun getImageUrl(): String {
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/" +
                "pokemon/other/official-artwork/${url?.extractId()}.png"
    }
}