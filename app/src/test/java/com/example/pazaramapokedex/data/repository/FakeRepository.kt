package com.example.pazaramapokedex.data.repository

import com.example.pazaramapokedex.data.remote.dto.Ability
import com.example.pazaramapokedex.data.remote.dto.AbilityX
import com.example.pazaramapokedex.data.remote.dto.OfficialArtwork
import com.example.pazaramapokedex.data.remote.dto.Other
import com.example.pazaramapokedex.data.remote.dto.PokemonBasicDto
import com.example.pazaramapokedex.data.remote.dto.PokemonDetailsDto
import com.example.pazaramapokedex.data.remote.dto.Result
import com.example.pazaramapokedex.data.remote.dto.Sprites
import com.example.pazaramapokedex.data.remote.dto.Stat
import com.example.pazaramapokedex.data.remote.dto.StatX
import com.example.pazaramapokedex.data.remote.dto.Type
import com.example.pazaramapokedex.data.remote.dto.TypeX
import com.example.pazaramapokedex.domain.repository.RepositoryInterface

class FakeRepository : RepositoryInterface {

    override suspend fun getPokemons(limit: Int, offset: Int): PokemonBasicDto {

        return PokemonBasicDto(1292,"https://pokeapi.co/api/v2/pokemon?offset=20&limit=20",null,
            listOf(Result("bulbasaur","https://pokeapi.co/api/v2/pokemon/1/"),Result("ivysaur","https://pokeapi.co/api/v2/pokemon/2/")))


    }

    override suspend fun getSinglePokemon(id: String): PokemonDetailsDto {
        return PokemonDetailsDto(listOf(Ability(AbilityX("overgrow","https://pokeapi.co/api/v2/ability/65/"))),64,64,7,"bulbasaur",1, Sprites(Other(OfficialArtwork("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png"))),
            listOf(Stat(45,StatX("hp","https://pokeapi.co/api/v2/stat/1/"))), listOf(Type(TypeX("grass","https://pokeapi.co/api/v2/type/12/"))),
            69)
    }
}