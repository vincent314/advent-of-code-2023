package me.vince.aoc.day3

class Map(lines: List<String>) {
    private val cells: Array<Array<Cell>> = lines.map { line ->
        line.map(::Cell).toTypedArray()
    }.toTypedArray()

    val partNumbers: List<PartNumber> = lines.flatMapIndexed { j: Int, line: String ->
        Regex("""\d+""")
            .findAll(line)
            .map { matchResult ->
                val value = matchResult.value
                val points = matchResult.range.map { i -> Point(i, j) }
                PartNumber(value.toInt(), points)
            }
    }

    operator fun get(x: Int, y: Int): Cell {
        return cells[y][x]
    }

    fun isAdjacent(partNumber: PartNumber): Boolean {
        return partNumber.coords.any { (x: Int, y: Int) ->
            listOf(
                this[x - 1, y - 1],
                this[x - 1, y + 1],
                this[x, y - 1],
                this[x - 1, y],
                this[x + 1, y],
                this[x, y + 1],
                this[x + 1, y + 1],
                this[x + 1, y - 1]
            ).any { cell -> cell.type == TypeEnum.COMPONENT }
        }
    }
}
