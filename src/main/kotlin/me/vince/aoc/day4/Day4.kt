package me.vince.aoc.day4

object Day4 {
    private val cardRegex = Regex("""Card\s+\d+:\s+((?:\d+\s*)+) \|\s+((:?\d+\s*)+)""")
    private val numbersSeparator = Regex("""\s+""")

    fun parseCard(input: String): Card {
        val (winningsStr, numbersStr) = cardRegex.find(input)?.destructured ?: throw IllegalArgumentException(input)
        return Card(
            winnings = winningsStr.split(numbersSeparator).map(String::toInt),
            numbers = numbersStr.split(numbersSeparator).map(String::toInt)
        )
    }

    fun total(cards: List<Card>): Long {
        return cards.sumOf(Card::score)
    }
}
