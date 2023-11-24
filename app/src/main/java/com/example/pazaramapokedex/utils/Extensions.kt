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

fun Int.formatId(): String {
    return "#" + String.format("%03d", this)
}

fun String.removeLeadingZeros(): String {

    var result = this.trimStart('0')

    if (result.startsWith("#")) {
        result = result.substring(1)
    }

    return result
}