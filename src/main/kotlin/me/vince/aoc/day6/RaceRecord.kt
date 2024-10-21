package me.vince.aoc.day6


data class RaceRecord(val time: Long, val distance: Long) {
    private fun computeDistance(pushTime: Long): Long = (time - pushTime) * pushTime

    fun countFaster(): Long =
        (0..time).count { t ->
            computeDistance(t) > distance
        }.toLong()

    fun findDicotomy(lowerBound: Long = 0, upperBound: Long = time, predicate: (time: Long) -> Boolean): Long {
        val halfTime = (lowerBound + upperBound) / 2
        val lbd = computeDistance(lowerBound)
        val ubd = computeDistance(upperBound)
        val htd = computeDistance(halfTime)

        if (upperBound - lowerBound <= 1) {
            return if (predicate(ubd)) upperBound else lowerBound
        }

        return when {
            lbd == distance -> return upperBound
            ubd == distance -> return lowerBound
            htd == distance -> if (lbd > ubd) lowerBound else upperBound
            htd < distance && lbd < distance -> findDicotomy(halfTime, upperBound, predicate)
            htd < distance && lbd > distance -> findDicotomy()
        }
    }

    fun countFasterPossibilities(): Long {
        val lower = findDicotomy { it > distance }
        val higher = findDicotomy { it < distance }
        println("$lower=${computeDistance(lower)} - $higher=${computeDistance((higher))}")
        return higher - lower
        //333163512891532
    }
}

