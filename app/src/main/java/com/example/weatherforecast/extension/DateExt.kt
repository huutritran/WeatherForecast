package com.example.weatherforecast.extension

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

fun Date.diffDays(another: Date): Long {
    val diff = abs(this.time - another.time)
    return diff / (24 * 60 * 60 * 1000)
}

fun Date.toDisplayString(format: String): String {
    val dateFormatter = SimpleDateFormat(format, Locale.getDefault())
    return dateFormatter.format(this)
}