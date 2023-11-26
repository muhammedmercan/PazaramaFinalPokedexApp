package com.example.pazaramapokedex.data.remote.dto


import com.google.gson.annotations.SerializedName

data class Ability(
    @SerializedName("ability")
    val ability: AbilityX?,
)