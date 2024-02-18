package me.vince.aoc.day4

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day4Test {
    val sample = """
        Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
        Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
        Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
        Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
        Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
        Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
    """.trimIndent().lines().map(Day4::parseCard)

    @Test
    fun `should parse card`() {
        val card = Day4.parseCard("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53")
        assertEquals(
            Card(
                winnings = listOf(41, 48, 83, 86, 17),
                numbers = listOf(83, 86, 6, 31, 17, 9, 48, 53)
            ), card
        )
    }

    @Test
    fun `should get card score`() {
        val card = Day4.parseCard("Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53")
        assertEquals(8, card.score)
    }

    @Test
    fun `should get total score`() {
        val total = Day4.total(sample)
        assertEquals(13, total)
    }

    fun readInput() =
        String(Day4Test::class.java.getResourceAsStream("/inputs/day4.txt").readAllBytes())
            .lines()
            .filter(String::isNotEmpty)
            .map(Day4::parseCard)

    @Test
    fun `should get total score part1`() {
        val cards = readInput()
        val total = Day4.total(cards)
        assertEquals(23028, total)
    }
}
