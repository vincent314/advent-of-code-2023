package me.vince.aoc.day8

import me.vince.aoc.TestUtil
import me.vince.aoc.day8.Instruction.LEFT
import me.vince.aoc.day8.Instruction.RIGHT
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class NavigationMapTest {
    private val samplePart1 = """
        LLR

        AAA = (BBB, BBB)
        BBB = (AAA, ZZZ)
        ZZZ = (ZZZ, ZZZ)
    """.trimIndent()

    private val samplePart2 = """
        LR

        11A = (11B, XXX)
        11B = (XXX, 11Z)
        11Z = (11B, XXX)
        22A = (22B, XXX)
        22B = (22C, 22C)
        22C = (22Z, 22Z)
        22Z = (22B, 22B)
        XXX = (XXX, XXX)
    """.trimIndent()

    @Test
    fun `should parse sample`() {
        val navigationMap = NavigationMap.from(samplePart1, NodeRulesPart1())

        assertEquals(listOf(LEFT, LEFT, RIGHT), navigationMap.instructions.asSequence().take(3).toList())

        val nodeRules = NodeRulesPart1()

        val aaa = Node("AAA", null, null, nodeRules)
        val bbb = Node("BBB", null, null, nodeRules)
        val zzz = Node("ZZZ", null, null, nodeRules)

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

        assertEquals("AAA : (BBB, BBB)", navigationMap.roots.first().toString())
        assertEquals("BBB : (AAA, ZZZ)", navigationMap.roots.first().left.toString())
        assertEquals("ZZZ : (ZZZ, ZZZ)", navigationMap.roots.first().left?.right.toString())
    }

    @Test
    fun `should find end of instructions`() {
        val navigationMap = NavigationMap.from(samplePart1, NodeRulesPart1())
        val result = navigationMap.reachEnd()
        assertEquals(6, result)
    }

    @Test
    fun `should find end of puzzle input`() {
        val input = TestUtil.readFile("/inputs/day8.txt")

        val navigationMap = NavigationMap.from(input, NodeRulesPart1())
        val result = navigationMap.reachEnd()
        assertEquals(20093L, result)
    }

    @Test
    fun `should find end of sample part 2`() {
        val navigationMap = NavigationMap.from(samplePart2, NodeRulesPart2())
        val result = navigationMap.reachEnd()
        assertEquals(6L, result)
    }

    @Test
    fun `should find end of puzzle input part 2`() {
        val input = TestUtil.readFile("/inputs/day8.txt")
        val navigationMap = NavigationMap.from(input, NodeRulesPart2())
        val result = navigationMap.reachEnd()
        assertEquals(22_103_062_509_257L, result)
    }
}
