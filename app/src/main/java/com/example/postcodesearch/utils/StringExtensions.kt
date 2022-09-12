package com.example.postcodesearch.utils

import java.text.Normalizer

//TODO change this name
private val UNACENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()

fun CharSequence.removeAccents(): String {
    val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
    return UNACENT.replace(temp, "")
}

fun String.capitalizeWorld(): String {
    val stringList = mutableListOf<String>()
    this.split(" ").forEach { stringPart ->
        stringList.add(stringPart.lowercase().replaceFirstChar { it.uppercase() })
    }

    return stringList.toString()
        .replace("[", "")
        .replace("]", "")
        .replace(",", "")
}