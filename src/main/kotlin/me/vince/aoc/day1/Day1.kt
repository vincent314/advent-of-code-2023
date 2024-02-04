package me.vince.aoc.day1

object Day1 {
    fun parseDigits(s: String): Int {
        val first = s.firstOrNull(Char::isDigit)
        val last = s.lastOrNull(Char::isDigit)
        if (first == null || last == null) return 0
        return "$first$last".toInt()
    }

    private val regexFirst = Regex("""(\d|zero|one|two|three|four|five|six|seven|eight|nine)""", setOf(RegexOption.IGNORE_CASE))

    private fun String.parse(): Int {
        return when (this) {
            "zero" -> 0
            "one" -> 1
            "two" -> 2
            "three" -> 3
            "four" -> 4
            "five" -> 5
            "six" -> 6
            "seven" -> 7
            "eight" -> 8
            "nine" -> 9
            else -> toInt()
        }
    }

    fun parseDigitsAlsoLetters(s: String): Int {
        val first = regexFirst.find(s)?.value?.parse()
        var last: Int? = null
        for (i in s.indices.reversed()) {
            last = regexFirst.find(s, i)?.value?.parse()
            if (last != null) break
        }
        if (first == null || last == null) return 0
        return "$first$last".toInt()
    }

    private fun sum(inputs: List<String>, parser: (s: String) -> Int): Int {
        return inputs.fold(0) { s, line -> s + parser(line) }
    }

    fun resolvePart1(inputs: List<String>): Int {
        return sum(inputs, ::parseDigits)
    }

    fun resolvePart2(inputs: List<String>): Int {
        return sum(inputs, ::parseDigitsAlsoLetters)
    }
}
