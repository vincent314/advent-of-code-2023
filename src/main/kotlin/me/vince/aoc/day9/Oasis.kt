package me.vince.aoc.day9

object Oasis {
    fun parse(input: String): List<Long> {
        return input.split(Regex("\\s+"))
            .map(String::toLong)
    }

    fun getDifferences(values: List<Long>): List<Long> {
        return values.zipWithNext { a, b -> b - a }
    }

    fun extrapolate(values: List<Long>): Long {
        if (values.all { it == 0L }) {
            return 0L
        }

        val differences = getDifferences(values)
        val extrapolation = extrapolate(differences)
        return values.last() + extrapolation
    }

    fun analyzeHistory(data: List<List<Long>>, backward: Boolean = false): Long {
        return if (!backward) {
            data.sumOf(::extrapolate)
        } else {
            data.map(List<Long>::reversed).sumOf(::extrapolate)
        }
    }
}
