package me.vince.aoc.day7

enum class Card(val code: Char) {
    JACK('J'), TWO('2'), THREE('3'), FOUR('4'), FIVE('5'), SIX('6'), SEVEN('7'), EIGHT('8'), NINE('9'), TEN('T'), QUEEN('Q'), KING('K'), ACE('A');

    companion object {
        fun from(char: Char): Card {
            return when (char) {
                '2' -> TWO
                '3' -> THREE
                '4' -> FOUR
                '5' -> FIVE
                '6' -> SIX
                '7' -> SEVEN
                '8' -> EIGHT
                '9' -> NINE
                'T' -> TEN
                'J' -> JACK
                'Q' -> QUEEN
                'K' -> KING
                'A' -> ACE
                else -> throw IllegalArgumentException("Invalid card character: $char")
            }
        }
    }
}

enum class HandType {
    HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_OF_A_KIND, FULL_HOUSE, FOUR_OF_A_KIND, FIVE_OF_A_KIND;

    val next: HandType
        get() = when (this) {
            HIGH_CARD -> ONE_PAIR
            ONE_PAIR -> THREE_OF_A_KIND
            TWO_PAIR -> FULL_HOUSE
            THREE_OF_A_KIND -> FOUR_OF_A_KIND
            FULL_HOUSE -> FOUR_OF_A_KIND
            FOUR_OF_A_KIND -> FIVE_OF_A_KIND
            FIVE_OF_A_KIND -> FIVE_OF_A_KIND
        }
}

class Day7(input: String) {

    private val hands: List<Hand> = parseInput(input)

    private fun parseInput(input: String) = input.lines().filter(String::isNotBlank).map { Hand.from(it) }

    data class Hand(
        val cards: List<Card>,
        val bid: Long,
    ) {
        companion object {
            fun from(str: String): Hand {
                val parts = str.split(" ")
                val cards = parts.first().map { Card.from(it) }
                val bid = parts.last().toLong()
                return Hand(cards, bid)
            }
        }

        val type: HandType
            get() {
                val counts = cards
                    .filterNot { it == Card.JACK }
                    .groupBy { it }
                    .values
                    .map { it.size }
                    .sortedDescending()
                val first = counts.getOrNull(0)
                val second = counts.getOrNull(1)
                var temporaryType = when {
                    first == 5 -> HandType.FIVE_OF_A_KIND
                    first == 4 -> HandType.FOUR_OF_A_KIND
                    first == 3 && second == 2 -> HandType.FULL_HOUSE
                    first == 3 && second != 2 -> HandType.THREE_OF_A_KIND
                    first == 2 && second == 2 -> HandType.TWO_PAIR
                    first == 2 && second == 1 -> HandType.ONE_PAIR
                    else -> HandType.HIGH_CARD
                }

                val totalJack = cards.count { it == Card.JACK }
                repeat(totalJack) {
                    temporaryType = temporaryType.next
                }
                return temporaryType
            }

        override fun toString(): String {
            val cardStr = cards.joinToString(separator = ""){ card:Card -> card.code.toString() }
            return "$cardStr $type"
        }
    }

    val sortedHands: List<Hand>
        get() = hands.sortedWith(
            compareBy(Hand::type)
                .thenComparing { left, right ->
                    val leftCards = left.cards
                    val rightCards = right.cards

                    leftCards.zip(rightCards)
                        .firstOrNull { (leftCard, rightCard) -> leftCard != rightCard }
                        ?.let { (leftCard, rightCard) -> compareValuesBy(leftCard, rightCard, Card::ordinal) }
                        ?: 0
                }
        )

    val totalWinning: Long
        get() = sortedHands
            .mapIndexed { index, hand -> (index + 1) * hand.bid }
            .sum()
}
