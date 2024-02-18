package me.vince.aoc.day4

import kotlin.math.pow

data class Card(
    val id: Int,
    val winnings: List<Int>,
    val numbers: List<Int>,
    var copies: List<Card> = listOf(),
) {
    val winCount: Int = numbers.count { it in winnings }

    val score: Long

    init {
        val size = numbers.count { it in winnings }
        score = if (size == 0) 0
        else 2.toDouble().pow(size.toDouble() - 1).toLong()
    }
}

