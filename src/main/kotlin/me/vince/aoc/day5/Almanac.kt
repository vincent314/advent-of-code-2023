package me.vince.aoc.day5

import kotlin.time.measureTimedValue

fun List<RangeMap>.findTarget(source: Long): Long {
    val value = firstOrNull { (_, sources) -> source in sources }
        ?.getConnection(source)
        ?: source
    return value
}

class Almanac(
    val seeds: List<Long>,
    val seedRange: List<LongRange>,
    val seedToSoilList: MutableList<RangeMap> = mutableListOf(),
    val soilToFertilizer: MutableList<RangeMap> = mutableListOf(),
    val fertilizerToWater: MutableList<RangeMap> = mutableListOf(),
    val waterToLight: MutableList<RangeMap> = mutableListOf(),
    val lightToTemperature: MutableList<RangeMap> = mutableListOf(),
    val temperatureToHumidity: MutableList<RangeMap> = mutableListOf(),
    val humidityToLocation: MutableList<RangeMap> = mutableListOf(),
) {
    fun process(seed: Long): Long {
        return seedToSoilList.findTarget(seed)
            .let(soilToFertilizer::findTarget)
            .let(fertilizerToWater::findTarget)
            .let(waterToLight::findTarget)
            .let(lightToTemperature::findTarget)
            .let(temperatureToHumidity::findTarget)
            .let(humidityToLocation::findTarget)
    }

    val lowestLocation: Long
        get() = seeds.minOf(::process)

    val lowestLocationRange: Long
        get() = seedRange.minOf { range ->
            val (min, duration) = measureTimedValue {
                range.minOf(::process)
            }
            println("Range ${range.first} - ${range.last} : $duration")
            min
        }
}
