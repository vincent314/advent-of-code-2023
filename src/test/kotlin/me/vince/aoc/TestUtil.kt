package me.vince.aoc

object TestUtil {
    fun readFile(fileName:String): String {
        return String(TestUtil::class.java.getResourceAsStream(fileName)!!.readAllBytes())
    }
}
