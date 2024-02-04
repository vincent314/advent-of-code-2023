package me.vince.aoc.day2

import java.lang.IllegalArgumentException

data class Subset(
    var blue: Int = 0,
    var red: Int = 0,
    var green: Int = 0,
) {
    operator fun set(s: String, value: Int) {
        when (s) {
            "blue" -> blue = value
            "red" -> red = value
            "green" -> green = value
            else -> throw IllegalArgumentException("Invalid property $s")
        }
    }
}

