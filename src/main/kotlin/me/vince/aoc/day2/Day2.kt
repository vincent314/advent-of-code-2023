package me.vince.aoc.day2

object Day2 {
    private val gameRegex = Regex("""Game (\d+)""")
    private val subsetRegex = Regex("""(\d+ blue)|(\d+ red)|(\d+ green)""")

    fun parseInput(line: String): Game {
        val (gameStr, subsetStr) = line.split(':', limit = 2)
        val gameId = gameRegex.find(gameStr)?.groupValues?.get(1)?.toInt()
        requireNotNull(gameId)

        val subsets = subsetStr.split(";")
            .map {
                val subset = Subset()
                subsetRegex.findAll(it.trim())
                    .map(MatchResult::value)
                    .forEach { str ->
                        val (value, color) = str.split(" ")
                        subset[color] = value.toInt()
                    }
                subset
            }

        return Game(gameId, subsets)
    }

    fun parseInput(lines: List<String>): List<Game> {
        return lines
            .filter { it.startsWith("Game") }
            .map(::parseInput)
    }

    fun filterValid(games: List<Game>, maximums: Subset): List<Game> {
        return games.filter { game ->
            game.subsets.all { subset ->
                subset.blue <= maximums.blue && subset.red <= maximums.red && subset.green <= maximums.green
            }
        }
    }

    fun resolvePart1(games: List<Game>, maximums: Subset): Int {
        return filterValid(games, maximums).fold(0) { sum, game -> sum + game.id }
    }

    val Game.maximum: Subset
        get() {
            return Subset(
                green = subsets.maxOfOrNull(Subset::green) ?: 0,
                red = subsets.maxOfOrNull(Subset::red) ?: 0,
                blue = subsets.maxOfOrNull(Subset::blue) ?: 0
            )
        }

    val Subset.power: Int
        get() = green * red * blue

    fun resolvePart2(games: List<Game>): Int {
        return games.sumOf { it.maximum.power }
    }
}
