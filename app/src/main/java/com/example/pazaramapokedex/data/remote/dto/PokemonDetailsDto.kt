package com.example.pazaramapokedex.data.remote.dto


import com.example.pokedex.domain.model.PokemonDetails
import com.google.gson.annotations.SerializedName

data class PokemonDetailsDto(
    @SerializedName("abilities")
    val abilities: List<Ability>?,
    @SerializedName("base_experience")
    val baseExperience: Int?,
    //@SerializedName("forms")
    //val forms: List<Form>?,
    //@SerializedName("game_indices")
    //val gameİndices: List<Gameİndice>?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("held_items")
    val heldİtems: List<Any>?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("is_default")
    val isDefault: Boolean?,
    @SerializedName("location_area_encounters")
    val locationAreaEncounters: String?,
    //@SerializedName("moves")
    //val moves: List<Move>?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("order")
    val order: Int?,
    @SerializedName("past_abilities")
    val pastAbilities: List<Any>?,
    @SerializedName("past_types")
    val pastTypes: List<Any>?,
    //@SerializedName("species")
    //val species: Species?,
    @SerializedName("sprites")
    val sprites: Sprites?,
    @SerializedName("stats")
    val stats: List<Stat>?,
    @SerializedName("types")
    val types: List<Type>?,
    @SerializedName("weight")
    val weight: Int?
)
    fun PokemonDetailsDto.toPokemonDetail() : PokemonDetails {

        return PokemonDetails(id!!,sprites?.other?.officialArtwork?.frontDefault,stats!!, name!!, height!!, weight!!, types?.map { types -> types.type?.name!! }!! )

    }
