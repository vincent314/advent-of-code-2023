package me.vince.aoc.day9

import me.vince.aoc.TestUtil
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class OasisTest {
    @Test
    fun `should test parse`() {
        val values = Oasis.parse("10  13  16  21  30  45  68")

        assertEquals(listOf(10L, 13L, 16L, 21L, 30L, 45L, 68L), values)
    }

    @Test
    fun `should get differences`() {
        val values = Oasis.parse("10  13  16  21  30  45  68")
        val differences = Oasis.getDifferences(values)

        assertEquals(listOf(3L, 3L, 5L, 9L, 15L, 23L), differences)
    }

    @ParameterizedTest
    @CsvSource(
        "0 0 0 0, 0",
        "3 3 3 3 3, 3",
        "0 3 6 9 12 15, 18",
        "1 3 6 10 15 21, 28",
        "10 13 16 21 30 45, 68"
    )
    fun `should extrapolate`(input: String, expected: Long) {
        val extrapolation = Oasis.extrapolate(input.split(" ").map(String::toLong))
        assertEquals(expected, extrapolation)
    }

    @Test
    fun `should resolve part 1`(){
        val data = TestUtil.readFile("/inputs/day9.txt")
            .lines()
            .filter { it.isNotBlank() }
            .map(Oasis::parse)

        val result = Oasis.analyzeHistory(data)

        assertEquals(1980437560L, result)
    }
}
