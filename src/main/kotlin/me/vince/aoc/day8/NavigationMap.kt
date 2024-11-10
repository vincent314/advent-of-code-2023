package me.vince.aoc.day8

import me.vince.aoc.day8.Instruction.*
import me.vince.aoc.lcm

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

interface INodeRules {
    fun isStart(value: String): Boolean
    fun isEnd(value: String): Boolean
}

class NodeRulesPart1 : INodeRules {
    override fun isStart(value: String): Boolean = value == "AAA"
    override fun isEnd(value: String): Boolean = value == "ZZZ"
}

class NodeRulesPart2 : INodeRules {
    override fun isStart(value: String): Boolean = value.endsWith('A')
    override fun isEnd(value: String): Boolean = value.endsWith('Z')
}

data class Node(
    val value: String,
    var left: Node?,
    var right: Node?,
    val nodeRules: INodeRules
) {
    val isStart = nodeRules.isStart(value)
    val isEnd = nodeRules.isEnd(value)

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

    fun next(step: Instruction): Node {
        return when (step) {
            LEFT -> left!!
            RIGHT -> right!!
        }
    }
}

class NavigationMap(
    val instructions: List<Instruction>,
    val roots: List<Node>
) {

    companion object {
        private val lineRegex = Regex("""(\w{3}) = \((\w{3}), (\w{3})\)""")

        fun from(input: String, nodeRules: INodeRules): NavigationMap {
            val instructions = input.lines()
                .first()
                .mapNotNull(Instruction.Companion::from)

            val nodeMap = parseNodes(input.lines().drop(1).filterNot(String::isBlank), nodeRules)

            val roots = nodeMap.values.filter(Node::isStart)

            return NavigationMap(instructions, roots)
        }

        private fun parseNodes(lines: List<String>, nodeRules: INodeRules): Map<String, Node> {
            val nodeMap = lines.mapNotNull { line ->
                lineRegex.matchEntire(line)?.let {
                    val (a, b, c) = it.destructured
                    Pair(a, Pair(b, c))
                }
            }
                .toMap()

            val nodes = nodeMap.mapValues { (key, _) ->
                Node(key, null, null, nodeRules)
            }

            nodeMap.forEach { (id, pair) ->
                val (leftId, rightId) = pair
                nodes[id]?.left = nodes[leftId]
                nodes[id]?.right = nodes[rightId]
            }

            return nodes
        }

    }

    fun reachEnd(): Long {
        val moveCounts = roots.map { rootNode ->
            var currentNode = rootNode
            val instructionIterator = instructionSequence(instructions).iterator()
            var moveCounter = 0L
            while (!currentNode.isEnd) {
                val instruction = instructionIterator.next()
                currentNode = currentNode.next(instruction)
                moveCounter++
            }
            moveCounter
        }
        return lcm(*moveCounts.toLongArray())
    }

    private fun instructionSequence(instructions: List<Instruction>) = sequence {
        while (true) {
            instructions.forEach { yield(it) }
        }
    }
}
