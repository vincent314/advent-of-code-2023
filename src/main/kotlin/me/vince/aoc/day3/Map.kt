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

    val components = partNumbers.filter { getSymbol(it) != null }

    operator fun get(x: Int, y: Int): Cell? {
        return if (y in cells.indices && x in cells[y].indices) cells[y][x]
        else null
    }

    private fun getSymbol(partNumber: PartNumber): Symbol? {
        return partNumber.coords.flatMap { (x: Int, y: Int) ->
            listOfNotNull(
                Point(x - 1, y - 1),
                Point(x - 1, y + 1),
                Point(x, y - 1),
                Point(x - 1, y),
                Point(x + 1, y),
                Point(x, y + 1),
                Point(x + 1, y + 1),
                Point(x + 1, y - 1),
            )
        }
            .find { (x: Int, y: Int) -> this[x, y]?.type == TypeEnum.COMPONENT }
            ?.let { (x: Int, y: Int) -> Symbol(this[x, y]!!, Point(x, y), partNumber) }
    }

    fun getGears(): List<Gear> {
        return partNumbers.mapNotNull(::getSymbol)
            .filter { it.symbol.value == '*' }
            .also { println(it) }
            .groupBy(Symbol::point)
            .filterValues { symbols -> symbols.size == 2 }
            .map { (point: Point, symbols: List<Symbol>) ->
                Gear(
                    point,
                    symbols[0].partNumber.value * symbols[1].partNumber.value
                )
            }
    }
}
