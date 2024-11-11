package me.vince.aoc.day10

import me.vince.aoc.TestUtil
import me.vince.aoc.day10.PipeEnum.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class MazeTest {
    private val simpleSample = """
            .....
            .S-7.
            .|.|.
            .L-J.
            .....
    """.trimIndent()
    private val complexeSample = """
            ..F7.
            .FJ|.
            SJ.L7
            |F--J
            LJ...
    """.trimIndent()

    @Test
    fun `should parse and print maze`() {
        val result = Maze.from(simpleSample).toString()
        println(result)

        assertEquals(simpleSample, result)
    }

    @Test
    fun `should find starting point`() {
        val startingPoint = Maze.from(complexeSample).startingPoint
        assertEquals(Position(0, 2), startingPoint)
    }

    @ParameterizedTest
    @CsvSource(
        "NORTH, 2, 1, BEND_SOUTH_EAST",
        "SOUTH, 2, 1, GROUND",
        "EAST, 2, 1, VERTICAL",
        "WEST, 2, 1, BEND_SOUTH_EAST"
    )
    fun `should get at a direction`(direction: DirectionEnum, x: Int, y: Int, expectedPipe: PipeEnum) {
        val maze = Maze.from(complexeSample)
        assertEquals(BEND_NORTH_WEST, maze[x, y])
        val result = maze.getAt(Position(x, y), direction)
        assertEquals(expectedPipe, result)
    }

    @ParameterizedTest
    @CsvSource(
        "0, 2, SOUTH, EAST",
        "3, 2, NORTH, EAST",
        "1, 4, NORTH, WEST",
    )
    fun `should get possible directions`(x: Int, y: Int, direction1: DirectionEnum, direction2: DirectionEnum) {
        val maze = Maze.from(complexeSample)
        val result = maze.getPossibleDirections(Position(x, y))
        assertEquals(listOf(direction1, direction2), result)
    }

    @Test
    fun `should navigate starting at the starting point`() {
        val maze = Maze.from(simpleSample)
        val matrix = maze.buildStepMatrix()
        maze.next(maze.startingPoint, matrix)

        val expected = """
            .....
            .076.
            .1.5.
            .234.
            .....
        """.trimIndent()
        assertEquals(expected, matrix.toString())
    }

    @Test
    fun `should get steps to the farthest point for simple sample`(){
        val maze = Maze.from(simpleSample)
        val result = maze.getStepsFartherstPoint()
        assertEquals(4,result)
    }

    @Test
    fun `should get steps to the farthest point for complex sample`(){
        val maze = Maze.from(complexeSample)
        val result = maze.getStepsFartherstPoint()
        assertEquals(8,result)
    }

    @Test
    fun `should get steps for puzzle input`(){
        val input = TestUtil.readFile("/inputs/day10.txt")
        val maze = Maze.from(input)
        val result = maze.getStepsFartherstPoint()
        assertEquals(6786,result)
    }
}
