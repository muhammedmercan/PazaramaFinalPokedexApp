package com.example.pazaramapokedex.data.remote.dto


import com.example.pazaramapokedex.utils.extractId
import com.example.pokedex.domain.model.PokemonBasic
import com.example.pokedex.domain.model.PokemonDetails
import com.google.gson.annotations.SerializedName

data class PokemonBasicDto(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: Any?,
    @SerializedName("results")
    val results: List<Result?>?
)

fun PokemonBasicDto.toPokemonBasic() : List<PokemonBasic>? {

    return results?.map { result -> PokemonBasic(result?.url?.extractId()!!,count,next,previous, result?.name,result.url) }
}
