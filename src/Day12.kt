import java.util.ArrayDeque

val valueByChar: Map<Char, Int> = ("S" + ('a'..'z').joinToString("") + "E").withIndex().associate { (k, v) -> v to k }

enum class Direction(val h: Int, val v: Int) { L(-1, 0), R(1, 0), U(0, -1), D(0, 1) }
data class Point(val h: Int, val v: Int, val c: Char, var d: Int? = null)

fun List<String>.toPoints() = withIndex().map { (v, s) -> s.withIndex().map { (h, c) -> Point(h, v, c) } }

fun Point.adjacentPoints(points: List<List<Point>>, compare: (Int, Int) -> Boolean) =
    Direction.values().mapNotNull {
        (v + it.v to h + it.h)
            .takeIf { (v1, h1) -> v1 in points.indices && h1 in points[0].indices }
            ?.let { (v1, h1) -> points[v1][h1] }
            ?.takeIf { compare(valueByChar[c]!!, valueByChar[it.c]!!) }
    }

fun solution(input: List<String>, start: Char, end: Char, compare: (Int, Int) -> Boolean): Int? {
    val points = input.toPoints()
    val startFromPoint = points.flatten().first { it.c == start }
    val queue = ArrayDeque<Point>().apply { add(startFromPoint.apply { d = 0 }) }
    while (queue.isNotEmpty()) {
        queue.removeFirst().apply {
            if (c == end) return d
            adjacentPoints(points, compare).filter { it.d == null }.forEach { it.d = d!! + 1; queue.addLast(it) }
        }
    }
    return null
}

fun main() {
    solution(readInput("Day12"), 'S', 'E') { i1, i2 -> i2 in 0..i1 + 1 }.let(::println)
    solution(readInput("Day12"), 'E', 'a') { i1, i2 -> i1 in 0..i2 + 1 }.let(::println)
}