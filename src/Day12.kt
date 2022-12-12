import java.util.ArrayDeque

val valueByChar: Map<Char, Int> = "SabcdefghijklmnopqrstuvwxyzE".withIndex().associate { (k, v) -> v to k }
val directions = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to +1)

data class Point(val h: Int, val v: Int, val c: Char, var d: Int? = null)

fun List<String>.toPoints(): List<List<Point>> = mapIndexed { v, s -> s.mapIndexed { h, c -> Point(h, v, c) } }

fun Point.adjPoints(points: List<List<Point>>, compare: (Int, Int) -> Boolean): List<Point> = directions
    .mapNotNull { (v1, h1) -> points.getOrNull(v + v1)?.getOrNull(h + h1) }
    .filter { compare(valueByChar[c]!!, valueByChar[it.c]!!) }

fun List<String>.solution(startChar: Char, endChar: Char, compare: (Int, Int) -> Boolean): Int? {
    val points: List<List<Point>> = toPoints()
    val startPoint: Point = points.flatten().first { it.c == startChar }
    val queue = ArrayDeque<Point>().apply { add(startPoint.apply { d = 0 }) }
    while (queue.isNotEmpty()) queue.removeFirst().apply {
        if (c == endChar) return d
        adjPoints(points, compare).filter { it.d == null }.forEach { it.d = d!! + 1; queue.addLast(it) }
    }
    return null
}

fun main() {
    readInput("Day12").solution('S', 'E') { i1, i2 -> i2 in 0..i1 + 1 }.let(::println)
    readInput("Day12").solution('E', 'a') { i1, i2 -> i1 in 0..i2 + 1 }.let(::println)
}