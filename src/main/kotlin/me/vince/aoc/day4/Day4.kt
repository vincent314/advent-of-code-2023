package me.vince.aoc.day4

object Day4 {
    private val cardRegex = Regex("""Card\s+(\d+):\s+((?:\d+\s*)+) \|\s+((:?\d+\s*)+)""")
    private val numbersSeparator = Regex("""\s+""")

    fun parseCard(input: String): Card {
        val (id, winningsStr, numbersStr) = cardRegex.find(input)?.destructured ?: throw IllegalArgumentException(input)
        return Card(
            id = id.toInt(),
            winnings = winningsStr.split(numbersSeparator).map(String::toInt),
            numbers = numbersStr.split(numbersSeparator).map(String::toInt)
        )
    }

    fun total(cards: List<Card>): Long {
        return cards.sumOf(Card::score)
    }

    fun buildTree(cards: List<Card>): List<Card> {
        cards.forEachIndexed { index, card ->
            card.copies = (1..card.winCount).map { j -> cards[index + j] }
        }
        return cards
    }

    fun countNodes(card: Card): Int {
        return 1 + card.copies.sumOf { countNodes(it) }
    }

    fun countNodes(cards:List<Card>):Int {
        return cards.sumOf(::countNodes)
    }
}
