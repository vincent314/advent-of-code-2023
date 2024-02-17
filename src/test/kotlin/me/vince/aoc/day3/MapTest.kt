package me.vince.aoc.day3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MapTest {
    private val sample = """
       467..114..
       ...*......
       ..35..633.
       ......#...
       617*......
       .....+.58.
       ..592.....
       ......755.
       ...$.*....
       .664.598..""".trimIndent()

    @Test
    fun `should parse input`() {
        val map = Map(sample.lines())

        assertEquals('*', map[3, 1]?.value)
        assertEquals('7', map[2, 4]?.value)
    }

    @Test
    fun `should get part numbers with coordinates`() {
        val map = Map(
            """
       467..114..
       ...*..27..""".trimIndent().lines()
        )

        assertEquals(
            listOf(
                PartNumber(467, listOf(Point(0, 0), Point(1, 0), Point(2, 0))),
                PartNumber(
                    114, listOf(Point(5, 0), Point(6, 0), Point(7, 0))
                ),
                PartNumber(
                    27, listOf(Point(6, 1), Point(7, 1))
                ),
            ), map.partNumbers
        )
    }

    @Test
    fun `should filter part numbers`() {
        val map = Map(
            """
       467..114..
       ...*..27..""".trimIndent().lines()
        )

        val partNumbers = map.components
        assertEquals(
            listOf(
                PartNumber(467, listOf(Point(0, 0), Point(1, 0), Point(2, 0))),
            ), partNumbers
        )
    }

    @Test
    fun `should resolve sample`() {
        val components = Map(sample.lines()).components
        val expected = listOf(
            PartNumber(value = 467, coords = listOf(Point(0, 0), Point(1, 0), Point(2, 0))),
            PartNumber(value = 35, coords = listOf(Point(2, 2), Point(3, 2))),
            PartNumber(value = 633, coords = listOf(Point(6, 2), Point(7, 2), Point(8, 2))),
            PartNumber(value = 617, coords = listOf(Point(0, 4), Point(1, 4), Point(2, 4))),
            PartNumber(value = 592, coords = listOf(Point(2, 6), Point(3, 6), Point(4, 6))),
            PartNumber(value = 755, coords = listOf(Point(6, 7), Point(7, 7), Point(8, 7))),
            PartNumber(value = 664, coords = listOf(Point(1, 9), Point(2, 9), Point(3, 9))),
            PartNumber(value = 598, coords = listOf(Point(5, 9), Point(6, 9), Point(7, 9)))
        )

        assertEquals(expected, components)

        assertEquals(4361, components.sumOf(PartNumber::value))
    }


    fun readInput(): List<String> {
        return String(MapTest::class.java.getResourceAsStream("/inputs/day3.txt").readAllBytes()).lines()
    }

    @Test
    fun `should resolve part1`() {
        val components = Map(readInput()).components

        assertEquals(556057, components.sumOf(PartNumber::value))
    }

    @Test
    fun `should resolve sample part2`() {
        val gears = Map(sample.lines()).getGears()

        assertEquals(listOf(
            Gear(Point(3,1),16345),
            Gear(Point(5,8),451490)
        ),gears)

        assertEquals(467835, gears.sumOf { it.ratio })
    }

    @Test
    fun `should resolve part2`() {
        val gears = Map(readInput()).getGears()

        assertEquals(82824352, gears.sumOf { it.ratio })
    }

}
