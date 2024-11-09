package me.vince.aoc.day8

import me.vince.aoc.TestUtil
import me.vince.aoc.day8.Instruction.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class NavigationMapTest {
    private val sample = """
        LLR

        AAA = (BBB, BBB)
        BBB = (AAA, ZZZ)
        ZZZ = (ZZZ, ZZZ)
    """.trimIndent()

    @Test
    fun `should parse sample`() {
        val navigationMap = NavigationMap.from(sample)

        assertEquals(listOf(LEFT, LEFT, RIGHT), navigationMap.instructions)

        val aaa = Node("AAA", null, null)
        val bbb = Node("BBB", null, null)
        val zzz = Node("ZZZ", null, null)

        with(aaa) {
            left = bbb
            right = bbb
        }

        with(bbb) {
            left = aaa
            right = zzz
        }

        with(zzz) {
            left = zzz
            right = zzz
        }

        assertEquals("AAA : (BBB, BBB)", navigationMap.root.toString())
        assertEquals("BBB : (AAA, ZZZ)", navigationMap.root.left.toString())
        assertEquals("ZZZ : (ZZZ, ZZZ)", navigationMap.root.left?.right.toString())
    }

    @Test
    fun `should find end of instructions`() {
        val navigationMap = NavigationMap.from(sample)
        val result = navigationMap.reachEnd()
        assertEquals(6, result)
    }

    @Test
    fun `should find end of puzzle input`(){
        val input = TestUtil.readFile("/inputs/day8.txt")

        val navigationMap = NavigationMap.from(input)
        val result = navigationMap.reachEnd()
        assertEquals(20093, result)
    }
}
