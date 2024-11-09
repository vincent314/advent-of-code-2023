package me.vince.aoc

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class MathUtilsKtTest {

    @ParameterizedTest
    @CsvSource(
        "4,6,12",
        "5,2,10",
        "6,10,30")
    fun `should find LCM of two values`(a: Long, b: Long, lcm:Long) {
        assertEquals(lcm,lcm(a,b))
    }

    @ParameterizedTest
    @CsvSource(
        "4,6,10,60"
    )
    fun `should find LCM of three values`(a: Long, b: Long, c:Long, lcm:Long) {
        assertEquals(lcm,lcm(a,b,c))
    }
}
