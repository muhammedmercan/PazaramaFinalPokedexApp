package com.example.pazaramapokedex.utils

import com.example.pazaramapokedex.R

object PokemonTypeUtils {

    fun getTypeColor(type: String): Int {
        println(type)
        return when (type) {
            "normal" -> R.color.type_normal
            "fighting" -> R.color.type_fighting
            "flying" -> R.color.type_flying
            "poison" -> R.color.type_poison
            "ground" -> R.color.type_ground
            "rock" -> R.color.type_rock
            "bug" -> R.color.type_bug
            "ghost" -> R.color.type_ghost
            "steel" -> R.color.type_steel
            "fire" -> R.color.type_fire
            "water" -> R.color.type_water
            "grass" -> R.color.type_grass
            "electric" -> R.color.type_electric
            "psychic" -> R.color.type_psychic
            "ice" -> R.color.type_ice
            "dragon" -> R.color.type_dragon
            "fairy" -> R.color.type_fairy
            "dark" -> R.color.type_dark
            else -> R.color.type_normal
        }
    }

}