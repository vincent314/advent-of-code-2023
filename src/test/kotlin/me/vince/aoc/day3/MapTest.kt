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
       ...${'$'}.*....
       .664.598..""".trimIndent()

    @Test
    fun `should parse input`() {
        val map = Map(sample.lines())

        assertEquals('*', map[3, 1].value)
        assertEquals('7', map[2, 4].value)
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
}
