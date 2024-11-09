package me.vince.aoc.day6

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day6Test {
    private val sample = """Time:      7  15   30
           Distance:  9  40  200
           """.trimIndent()

    private val input = String(Day6::class.java.getResourceAsStream("/inputs/day6.txt").readAllBytes())

    @Test
    fun `should parse sample`() {

        val result = Day6.parse(sample, false)

        assertEquals(
            listOf(
                RaceRecord(7, 9),
                RaceRecord(15, 40),
                RaceRecord(30, 200),
            ), result
        )
    }

    @Test
    fun `should count faster options`() {
        val result = RaceRecord(7, 9).countFaster()
        assertEquals(4, result)
    }

    @Test
    fun `should resolve sample`() {
        val raceRecords = Day6.parse(sample, false)
        val result = Day6.resolve(raceRecords)
        assertEquals(288, result)
    }

    @Test
    fun `should resolve part1`() {
        val raceRecords = Day6.parse(
            input,
            false
        )
        val result = Day6.resolve(raceRecords)
        assertEquals(140220L, result)
    }

//    @Test
//    fun `should parse input fixing kerning`() {
//        val raceRecords = Day6.parse(sample, true)
//
//        assertEquals(listOf(RaceRecord(71530, 940200)), raceRecords)
//    }

//    @Test
//    fun `should find lower bound sample`() {
//        val raceRecord = Day6.parse(sample, true).first()
//        val lower = raceRecord.findDicotomy(true)
//        assertEquals(14, lower)
//    }

//    @Test
//    fun `should find upper bound sample`() {
//        val raceRecord = Day6.parse(sample, true).first()
//        val higher = raceRecord.findDicotomy(false)
//        assertEquals(71516, higher)
//    }

    @Test
    fun `should count faster possibilities`() {
        val raceRecord = Day6.parse(sample, true).first()
        val count = raceRecord.countFaster()
        assertEquals(71503L, count)
    }

    @Test
    fun `should resolve part2`() {
        val raceRecord = Day6.parse(input, true).first()
        val count = raceRecord.countFasterPossibilities()
        assertEquals(53_837_288L, raceRecord.time)
        assertEquals(333_163_512_891_532L, raceRecord.distance)
        assertEquals(39570184L, count)
    }
}
