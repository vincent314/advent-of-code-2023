package me.vince.aoc.day3

enum class TypeEnum {
    NUMBER, COMPONENT, EMPTY
}

@JvmInline
value class Cell(val value: Char) {
    val type: TypeEnum
        get() = when {
            value.isDigit() -> TypeEnum.NUMBER
            value == '.' -> TypeEnum.EMPTY
            else -> TypeEnum.COMPONENT
        }
}

