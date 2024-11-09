package me.vince.aoc.day8

import me.vince.aoc.day8.Instruction.*

enum class Instruction {
    LEFT, RIGHT;

    companion object {
        fun from(char: Char) = when (char) {
            'L' -> LEFT
            'R' -> RIGHT
            else -> throw IllegalArgumentException("Unknown char: $char")
        }
    }
}

data class Node(val value: String, var left: Node?, var right: Node?) {
    override fun toString(): String {
        return "$value : (${left?.value}, ${right?.value})"
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Node) return false
        return value == other.value
    }

    override fun hashCode(): Int {
        var result = value.hashCode()
        result = 31 * result + (left?.hashCode() ?: 0)
        result = 31 * result + (right?.hashCode() ?: 0)
        return result
    }
}

class NavigationMap(val instructions: List<Instruction>, val root: Node) {

    companion object {
        private val lineRegex = Regex("""(\w{3}) = \((\w{3}), (\w{3})\)""")

        fun from(input: String): NavigationMap {
            val instructions = input.lines()
                .first()
                .mapNotNull(Instruction.Companion::from)

            val nodeMap = parseNodes(input.lines().drop(1).filterNot(String::isBlank))

            return NavigationMap(instructions, requireNotNull(nodeMap["AAA"]))
        }

        private fun parseNodes(lines: List<String>): Map<String, Node> {
            val nodeMap = lines.mapNotNull { line ->
                lineRegex.matchEntire(line)?.let {
                    val (a, b, c) = it.destructured
                    Pair(a, Pair(b, c))
                }
            }
                .toMap()

            val nodes = nodeMap.mapValues { (key, _) ->
                Node(key, null, null)
            }

            nodeMap.forEach { (id, pair) ->
                val (leftId, rightId) = pair
                nodes[id]?.left = nodes[leftId]
                nodes[id]?.right = nodes[rightId]
            }

            return nodes
        }
    }

    fun reachEnd(): Int {
        var current: Node? = root
        var i = 0
        var moveNumber = 0

        while (current?.value != "ZZZ") {
            if (i !in instructions.indices) {
                i = 0
            }
            val instruction = instructions[i]
            current = when (instruction) {
                LEFT -> current?.left
                RIGHT -> current?.right
            }
            i++
            moveNumber++
        }
        return moveNumber
    }
}
