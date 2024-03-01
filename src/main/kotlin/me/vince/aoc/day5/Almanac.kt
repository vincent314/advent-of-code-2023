package me.vince.aoc.day5

import kotlin.time.measureTimedValue

fun List<RangeMap>.findTarget(source: Long): Long {
    val (value, duration) = measureTimedValue {
        firstOrNull { (_, sources) -> source in sources }
            ?.let { (destination, sources) ->
                val idx = sources.indexOf(source)
                destination.elementAt(idx)
            }
            ?: source
    }
    println("findTarget $source : $duration")
    return value
}

class Almanac(
    val seeds: List<Long>,
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
}
