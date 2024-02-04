package me.vince.aoc.day2

import me.vince.aoc.day2.Day2.maximum
import me.vince.aoc.day2.Day2.power
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day2Test {

    val sample = """
        Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
        Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
        Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
        Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green""".trimIndent()

    fun readInput(): List<String> {
        return String(Day2.javaClass.getResourceAsStream("/inputs/day2.txt").readAllBytes()).lines()
    }

    @Test
    fun `should parse input line`() {
        val game = Day2.parseInput("Game 12: 7 blue, 9 red, 1 green; 8 green; 10 green, 5 blue, 3 red")

        val expected = Game(
            12, listOf(
                Subset(7, 9, 1),
                Subset(green = 8),
                Subset(green = 10, blue = 5, red = 3)
            )
        )
        assertEquals(expected, game)
    }

    @Test
    fun `should filter valid games only`() {
        val games = Day2.parseInput(sample.lines())

        val result = Day2.filterValid(
            games, Subset(
                red = 12,
                green = 13,
                blue = 14,
            )
        )

        assertEquals(listOf(1, 2, 5), result.map(Game::id))
    }

    @Test
    fun `should resolve sample`() {
        val games = Day2.parseInput(sample.lines())

        val result = Day2.resolvePart1(
            games, Subset(
                red = 12,
                green = 13,
                blue = 14,
            )
        )

        assertEquals(8, result)
    }

    @Test
    fun `should resolve part 1`() {
        val games = Day2.parseInput(readInput())

        val result = Day2.resolvePart1(
            games, Subset(
                red = 12,
                green = 13,
                blue = 14,
            )
        )

        assertEquals(2551, result)
    }

    @Test
    fun `should get minimum requirement for a game`() {
        val game = Day2.parseInput("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green")
        assertEquals(Subset(red = 4, green = 2, blue = 6), game.maximum)
    }

    @Test
    fun `should get the power of a subset`() {
        val expected = Subset(red = 4, green = 2, blue = 6).power
        assertEquals(48, expected)
    }

    @Test
    fun `should resolve sample part2`() {
        val expected = Day2.resolvePart2(Day2.parseInput(sample.lines()))
        assertEquals(2286, expected)
    }

    @Test
    fun `should resolve part2`() {
        val expected = Day2.resolvePart2(Day2.parseInput(readInput()))
        assertEquals(62811, expected)
    }
}
