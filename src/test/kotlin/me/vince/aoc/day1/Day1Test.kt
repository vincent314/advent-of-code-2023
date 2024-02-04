package me.vince.aoc.day1

import me.vince.aoc.day1.Day1.parseDigits
import me.vince.aoc.day1.Day1.parseDigitsAlsoLetters
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day1Test {

    @Test
    fun `should get line digit`() {
        assertEquals(38, parseDigits("pqr3stu8vwx"))
        assertEquals(15, parseDigits("a1b2c3d4e5f"))
        assertEquals(77, parseDigits("treb7uchet"))
    }

    @Test
    fun `should sum parsed digits - sample1`() {
        val inputs = """ 1abc2
            pqr3stu8vwx
            a1b2c3d4e5f
            treb7uchet
        """.trimIndent()
            .lines()
        val result = Day1.resolvePart1(inputs)
        assertEquals(142, result)
    }

    fun readInput(): List<String> {
        return String(
            Day1.javaClass.getResourceAsStream("/inputs/day1.txt")
                .readAllBytes()
        ).lines()
    }

    @Test
    fun `should resolve input`() {
        val input = readInput()
        val result = Day1.resolvePart1(input)
        assertEquals(53080, result)
    }

    @Test
    fun `should parse with letters`() {
        assertEquals(29, parseDigitsAlsoLetters("two1nine"))
        assertEquals(83, parseDigitsAlsoLetters("eightwothree"))
        assertEquals(76, parseDigitsAlsoLetters("7pqrstsixteen"))
        assertEquals(77, parseDigitsAlsoLetters("7r"))
        assertEquals(22, parseDigitsAlsoLetters("z2"))
    }

    @Test
    fun `should resolve part2`() {
        val input = readInput()
        val result = Day1.resolvePart2(input)
        assertEquals(53268, result)
    }

    @Test
    fun `should resolve sample`() {
        val input = """
            two1nine
            eightwothree
            abcone2threexyz
            xtwone3four
            4nineeightseven2
            zoneight234
            7pqrstsixteen""".trimIndent()
        val result = Day1.resolvePart2(input.lines())
        assertEquals(281, result)
    }
}
