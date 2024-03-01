package me.vince.aoc.day5

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.Ignore
import kotlin.time.measureTimedValue

class Day5Test {
    private val sample = """
        seeds: 79 14 55 13

        seed-to-soil map:
        50 98 2
        52 50 48

        soil-to-fertilizer map:
        0 15 37
        37 52 2
        39 0 15

        fertilizer-to-water map:
        49 53 8
        0 11 42
        42 0 7
        57 7 4

        water-to-light map:
        88 18 7
        18 25 70

        light-to-temperature map:
        45 77 23
        81 45 19
        68 64 13

        temperature-to-humidity map:
        0 69 1
        1 0 69

        humidity-to-location map:
        60 56 37
        56 93 4
    """.trimIndent().lines()

    private val sampleAlmanac = Day5.parse(sample)

    private fun readInput(): Almanac {
        return Day5.parse(
            String(Day5Test::class.java.getResourceAsStream("/inputs/day5.txt").readAllBytes()).lines()
        )
    }

    @Test
    fun `should parse input`() {
        assertEquals(listOf(79L, 14L, 55L, 13L), sampleAlmanac.seeds)

        assertEquals(
            listOf(
                50L..<52L to 98L..<100L,
                52L..<100L to 50L..<98L,
            ), sampleAlmanac.seedToSoilList
        )
        assertEquals(
            listOf(
                0L..<37L to 15L..<52L,
                37L..<39L to 52L..<54L,
                39L..<54L to 0L..<15L
            ), sampleAlmanac.soilToFertilizer
        )
        assertEquals(
            listOf(
                60L..<97L to 56L..<93L,
                56L..<60L to 93L..<97L,
            ), sampleAlmanac.humidityToLocation
        )
    }

    @Test
    fun `should find target`() {
        mapOf(
            0 to 0,
            1 to 1,
            48 to 48,
            49 to 49,
            50 to 52,
            51 to 53,
            96 to 98,
            97 to 99,
            98 to 50,
            99 to 51,
        ).forEach { (s, t) ->
            assertEquals(t.toLong(), sampleAlmanac.seedToSoilList.findTarget(s.toLong()))
        }
    }

    @ParameterizedTest
    @CsvSource(
        value = [
            "79,82",
            "14,43",
            "55,86",
            "13,35"
        ]
    )
    fun `should find location of seed`(seed: Long, location: Long) {
        val result = sampleAlmanac.process(seed)
        assertEquals(location, result)
    }

    @Test
    fun `should find sample lowest location`() {
        val result = sampleAlmanac.lowestLocation
        assertEquals(35, result)
    }

    @Test
    fun `resolve part1 find lowest target`() {
        val almanac = readInput()

        val (result, duration)=measureTimedValue {
            almanac.lowestLocation
        }
        assertEquals(84470622L, result)

        println("Total duration : $duration")
    }
}
