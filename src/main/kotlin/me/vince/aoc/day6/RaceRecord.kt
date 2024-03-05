package me.vince.aoc.day6


data class RaceRecord(val time: Long, val distance: Long) {
    private fun computeDistance(pushTime: Long): Long = (time - pushTime) * pushTime

    fun countFaster(): Long =
        (0..time).count { t ->
            computeDistance(t) > distance
        }.toLong()

    fun findDicotomy(searchUpper: Boolean, lowerBound: Long = 0, upperBound: Long = time): Long {
        val halfTime = (lowerBound + upperBound) / 2
        val lbd = computeDistance(lowerBound)
        val ubd = computeDistance(upperBound)
        val htd = computeDistance(halfTime)

        if (upperBound - lowerBound <= 1) {
            return if (searchUpper) upperBound else lowerBound
        }

        return when {
            lbd == distance -> if (searchUpper) lbd + 1 else lbd - 1
            ubd == distance -> if (searchUpper) ubd + 1 else ubd - 1
            htd == distance -> if (searchUpper) htd + 1 else htd - 1
            searchUpper && htd > distance -> findDicotomy(searchUpper, lowerBound, halfTime)
            !searchUpper && htd < distance -> findDicotomy(searchUpper, lowerBound, halfTime)
            else -> findDicotomy(searchUpper, halfTime, upperBound)
        }
    }

    fun countFasterPossibilities(): Long {
        val lower = findDicotomy(true)
        val higher = findDicotomy(false)
        println("$lower=${computeDistance(lower)} - $higher=${computeDistance((higher))}")
        return higher - lower
        //333163512891532
    }
}

