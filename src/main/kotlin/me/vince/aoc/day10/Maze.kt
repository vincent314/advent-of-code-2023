package me.vince.aoc.day10

import me.vince.aoc.day10.DirectionEnum.*
import me.vince.aoc.day10.PipeEnum.*

enum class PipeEnum(val char: Char) {
    VERTICAL('|'), HORIZONTAL('-'), BEND_NORTH_EAST('L'), BEND_SOUTH_EAST('F'), BEND_NORTH_WEST('J'), BEND_SOUTH_WEST('7'), GROUND('.'), STARTING_POINT(
        'S'
    );

    companion object {
        fun from(char: Char): PipeEnum {
            return PipeEnum.entries.find { it.char == char } ?: throw IllegalArgumentException("Unknown char: $char")
        }
    }
}

enum class DirectionEnum {
    NORTH, SOUTH, EAST, WEST
}

data class Position(val x: Int, val y: Int) {
    fun next(direction: DirectionEnum): Position {
        return when (direction) {
            NORTH -> Position(x, y - 1)
            SOUTH -> Position(x, y + 1)
            EAST -> Position(x + 1, y)
            WEST -> Position(x - 1, y)
        }
    }
}


class StepMatrix(private val width: Int, height: Int) {
    private val matrix = Array<Int?>(width * height) { null }

    operator fun get(x: Int, y: Int): Int? {
        return matrix[y * width + x]
    }

    operator fun set(x: Int, y: Int, value: Int?) {
        matrix[y * width + x] = value
    }

    override fun toString(): String {
        return matrix.toList()
            .map { it ?: '.' }
            .chunked(width)
            .joinToString("\n") { it.joinToString("") }
    }

    val maxStep: Int
        get() = matrix.maxOf { it ?: 0 }
}

class Maze(val map: Array<Array<PipeEnum>>) {
    private val height = map.size
    private val width = map.first().size
    private val flatten = map.flatMap { it.asIterable() }

    companion object {
        fun from(input: String): Maze {
            val map = input.lines().map { line -> line.map(PipeEnum.Companion::from).toTypedArray() }.toTypedArray()
            return Maze(map)
        }
    }

    val startingPoint: Position
        get() {
            val idx = flatten.indexOf(STARTING_POINT)
            return Position(idx % width, idx / width)
        }

    override fun toString(): String {
        return flatten.map { it.char }
            .chunked(width)
            .joinToString("\n") { it.joinToString("") }
    }

    operator fun get(x: Int, y: Int): PipeEnum? {
        if (x !in (0..<width) || y !in (0..<height)) {
            return null
        }
        return flatten[y * width + x]
    }

    fun getAt(position: Position, direction: DirectionEnum): PipeEnum? {
        val (x, y) = position.next(direction)
        return this[x, y]
    }

    fun getPossibleDirections(position: Position): List<DirectionEnum> {
        val current = this[position.x, position.y]
        return when (current) {
            HORIZONTAL -> listOf(EAST, WEST)
            VERTICAL -> listOf(NORTH, SOUTH)
            BEND_NORTH_EAST -> listOf(NORTH, EAST)
            BEND_SOUTH_EAST -> listOf(SOUTH, EAST)
            BEND_NORTH_WEST -> listOf(NORTH, WEST)
            BEND_SOUTH_WEST -> listOf(SOUTH, WEST)
            STARTING_POINT -> getPossibleDirectionsAtStartingPoint(position)
            else -> throw InvalidPositionException("Invalid position $current as ${position.x}, ${position.y}  ")
        }
    }

    private fun getPossibleDirectionsAtStartingPoint(position: Position): List<DirectionEnum> {
        return DirectionEnum.entries
            .filter { direction ->
                val current = getAt(position, direction) ?: return@filter false
                when (direction) {
                    NORTH -> current in arrayOf(VERTICAL, BEND_SOUTH_EAST, BEND_SOUTH_WEST)
                    SOUTH -> current in arrayOf(VERTICAL, BEND_NORTH_EAST, BEND_NORTH_WEST)
                    EAST -> current in arrayOf(HORIZONTAL, BEND_SOUTH_WEST, BEND_NORTH_WEST)
                    WEST -> current in arrayOf(HORIZONTAL, BEND_SOUTH_EAST, BEND_NORTH_EAST)
                }
            }
    }

    fun buildStepMatrix(): StepMatrix {
        return StepMatrix(width, height)
    }

    tailrec fun next(currentPosition: Position, matrix: StepMatrix, step: Int = 0) {
        val current = this[currentPosition.x, currentPosition.y]
        val currentStep = matrix[currentPosition.x, currentPosition.y]
        if (current == STARTING_POINT) {
            if (currentStep != null) return
        }
        matrix[currentPosition.x, currentPosition.y] = step
        val nextPosition = getPossibleDirections(currentPosition)
            .map(currentPosition::next)
            .find { (x, y) -> matrix[x, y] == null }
            ?: return

        next(nextPosition, matrix, step + 1)
    }

    fun getStepsFartherstPoint(): Int {
        val matrix = buildStepMatrix()
        next(startingPoint, matrix)
        return matrix.maxStep / 2 + 1
    }
}
