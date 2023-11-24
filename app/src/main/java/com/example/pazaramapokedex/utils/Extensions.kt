package com.example.pazaramapokedex.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.pazaramapokedex.R

fun String.extractId() = this.substringAfter("pokemon").replace("/", "").toInt()

fun String.capitalize(): String {
    return this.replaceFirstChar { it.uppercaseChar() }
}

fun ImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .error(R.drawable.pokeball_large)
        .into(this)
}

fun String.formatId(id: Int): String {
    return "#" + String.format("%03d", id)
}