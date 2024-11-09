package me.vince.aoc.day7

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.io.File
import kotlin.test.Ignore
import kotlin.test.Test

class Day7Test {
    private val sample = """
                    32T3K 765
                    T55J5 684
                    KK677 28
                    KTJJT 220
                    QQQJA 483""".trimIndent()


    @ParameterizedTest
    @CsvSource(
        "JJJJJ 0,FIVE_OF_A_KIND",
        "AA2AA 42, FOUR_OF_A_KIND",
        "T5525 45, THREE_OF_A_KIND",
        "KT33T 50, TWO_PAIR",
        "32T3K 51, ONE_PAIR",
        "AK456 1, HIGH_CARD"
    )
    fun `must get the correct hand type`(handBid: String, expectedType: HandType) {
        val hand = Day7.Hand.from(handBid)
        assertEquals(expectedType, hand.type)
    }


    @Test
    fun `should sort hands`() {
        val day7 = Day7(sample)
        val sorted = day7.sortedHands

        assertEquals(
            listOf("32T3K", "KK677", "T55J5", "QQQJA", "KTJJT"),
            sorted.map { it.cards.map(Card::code).joinToString("") })
    }

    @Test
    fun `should sort hands of same type`() {
        val day7 = Day7(
            """
            QQQ6K 40
            66666 41
            22222 43
            QQQ5T 45
            88888 44
            78KKK 47
        """.trimIndent()
        )
        val sorted = day7.sortedHands

        assertEquals(
            listOf("78KKK", "QQQ5T", "QQQ6K", "22222", "66666", "88888"),
            sorted.map { it.cards.map(Card::code).joinToString("") })
    }

    @Ignore
    @Test
    fun `should calculate the winner`() {
        val day7 = Day7(sample)
        val winner = day7.totalWinning

        assertEquals(6440, winner)
    }

    @Test
    fun `should calculate the winner for puzzle`() {
        val input = String(Day7::class.java.getResourceAsStream("/inputs/day7.txt")!!.readAllBytes())
        val day7 = Day7(input)

        File("day7_output.txt").writeText(
            day7.sortedHands.joinToString("\n", transform = Day7.Hand::toString)
        )

        val total = day7.totalWinning

        assertEquals(251_062_184L, total)
    }

    @Test
    fun `should calculate the winner for sample with jacks`() {
        val result = Day7(sample).totalWinning
        assertEquals(5905, result)
    }

    @ParameterizedTest
    @CsvSource(
        "32T3K 765,ONE_PAIR",
        "AA2AA 42, FOUR_OF_A_KIND",
        "T55J5 45, FOUR_OF_A_KIND",
        "KTJJT 50, FOUR_OF_A_KIND",
        "JJJJJ 20, FIVE_OF_A_KIND",
        "9A7JJ 11, THREE_OF_A_KIND",
    )
    fun `should get the hand type for hands with jacks`(input: String, expectedType: HandType) {
        val hand = Day7.Hand.from(input)
        assertEquals(expectedType, hand.type)
    }

    @Test
    fun `should sort hands with jacks`() {
        var hands = Day7(
            """
            QJJQ2 10
            JKKK2 20
        """.trimIndent()
        )

        assertEquals(
            listOf(Day7.Hand.from("JKKK2 20"), Day7.Hand.from("QJJQ2 10")),
            hands.sortedHands
        )
        assertEquals(HandType.FOUR_OF_A_KIND, Day7.Hand.from("QJJQ2 42").type)
        assertEquals(HandType.FOUR_OF_A_KIND, Day7.Hand.from("JKKK2 42").type)

        hands = Day7(
            """
            T55J5 01
            KTJJT 02
            QQQJA 03
            """.trimIndent()
        )
        assertEquals(
            listOf(Day7.Hand.from("T55J5 01"), Day7.Hand.from("QQQJA 03"), Day7.Hand.from("KTJJT 02")),
            hands.sortedHands
        )
    }

}
