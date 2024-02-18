package me.vince.aoc.day4

import kotlin.math.pow

data class Card(val winnings: List<Int>, val numbers: List<Int>) {
    val score: Long
        get() {
            val size = numbers.count { it in winnings }
            return if (size == 0) 0
            else 2.toDouble().pow(size.toDouble() - 1).toLong()
        }
}

