package me.vince.aoc.day6

object Day6 {
    private fun parseLine(line: String, prefix: String, fixKerning: Boolean): List<Long> {
        return line.substringAfter(prefix)
            .trim()
            .let {
                if (fixKerning) {
                    it.filter { c -> c != ' ' }
                } else {
                    it
                }
            }
            .split(Regex("\\s+"))
            .map(String::toLong)
    }

    fun parse(input: String, fixKerning: Boolean): List<RaceRecord> {
        val (timeStr, distanceStr) = input.lines()
        val times = parseLine(timeStr, "Time:", fixKerning)
        val distances = parseLine(distanceStr, "Distance:", fixKerning)

        return times.zip(distances)
            .map { (time, distance) -> RaceRecord(time, distance) }
    }

    fun resolve(races: List<RaceRecord>): Long {
        return races.map(RaceRecord::countFaster)
            .reduce { left, right -> left * right }
    }
}
