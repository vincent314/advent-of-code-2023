package me.vince.aoc.day5

import me.vince.aoc.day5.Day5.MapTypeEnum.*

typealias RangeMap = Pair<LongRange, LongRange>

object Day5 {

    enum class MapTypeEnum {
        SEED_TO_SOIL, SOIL_TO_FERTILIZER, FERTILIZER_TO_WATER, WATER_TO_LIGHT, LIGHT_TO_TEMPERATURE, TEMPERATURE_TO_HUMIDITY, HUMIDITY_TO_LOCATION
    }

    private val dataLineRegex = Regex("""(\d+) (\d+) (\d+)""")

    fun parse(lines: List<String>): Almanac {
        val seeds = lines.first()
            .substringAfter(": ")
            .split(' ')
            .map(String::toLong)

        val almanac = Almanac(seeds = seeds)

        var currentType: MapTypeEnum? = null
        for (line in lines.drop(1)) {
            currentType = when (line) {
                "seed-to-soil map:" -> SEED_TO_SOIL
                "soil-to-fertilizer map:" -> SOIL_TO_FERTILIZER
                "fertilizer-to-water map:" -> FERTILIZER_TO_WATER
                "water-to-light map:" -> WATER_TO_LIGHT
                "light-to-temperature map:" -> LIGHT_TO_TEMPERATURE
                "temperature-to-humidity map:" -> TEMPERATURE_TO_HUMIDITY
                "humidity-to-location map:" -> HUMIDITY_TO_LOCATION
                else -> currentType
            }

            dataLineRegex.matchEntire(line)
                ?.destructured
                ?.let { (destination, source, length) ->
                    val d = destination.toLong()
                    val s = source.toLong()
                    val l = length.toLong()
                    RangeMap(
                        d..<d + l,
                        s..<s + l,
                    )
                }
                ?.also { rangeMap ->
                    when (currentType) {
                        SEED_TO_SOIL -> almanac.seedToSoilList += rangeMap
                        SOIL_TO_FERTILIZER -> almanac.soilToFertilizer += rangeMap
                        FERTILIZER_TO_WATER -> almanac.fertilizerToWater += rangeMap
                        WATER_TO_LIGHT -> almanac.waterToLight += rangeMap
                        LIGHT_TO_TEMPERATURE -> almanac.lightToTemperature += rangeMap
                        TEMPERATURE_TO_HUMIDITY -> almanac.temperatureToHumidity += rangeMap
                        HUMIDITY_TO_LOCATION -> almanac.humidityToLocation += rangeMap
                        else -> {}
                    }
                }
        }
        return almanac
    }
}
