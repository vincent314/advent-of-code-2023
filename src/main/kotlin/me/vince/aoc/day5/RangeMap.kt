package me.vince.aoc.day5

data class RangeMap(
    val destination: LongRange,
    val source: LongRange,
){
    fun getConnection(seed:Long):Long{
        val idx = (seed - source.first)
        return destination.first + idx
    }
}
