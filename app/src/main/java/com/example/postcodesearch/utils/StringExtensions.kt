package com.example.postcodesearch.utils

import java.text.Normalizer

private val REGEX_UNACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()
private val POSTAL_MASK = "####-###"
private val STRING_REGEX = "[^A-Za-z0-9]"
private val MASK_CHAR = "#"

fun CharSequence.removeAccents(): String {
    val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
    return REGEX_UNACCENT.replace(temp, "")
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

fun String.adjustQuery(): String {
    val stringList = this.trim().split(STRING_REGEX.toRegex())
    val postal = stringList[0].maskPostal()
    val name = if(stringList.size >= 1) stringList[1] else ""
    return "$postal $name"
}

fun String.maskPostal(): String {
    val unmaskedValue = this.unmask()
    var maskedValue = ""
    var i = 0

    POSTAL_MASK.toCharArray().forEach { m ->
        if (i >= unmaskedValue.length) return@forEach

        if (m.toString() != MASK_CHAR) {
            maskedValue += m
        } else {
            maskedValue += unmaskedValue[i]
            i++
        }
    }

    return maskedValue
}

private val unmaskRegex = Regex(STRING_REGEX)

fun String.unmask(): String {
    return unmaskRegex.replace(this, "")
}