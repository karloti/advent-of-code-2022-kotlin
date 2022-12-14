import kotlin.math.absoluteValue
import kotlin.math.sign

class Day14(private val input: Sequence<String>) {
    private val String.numbers get() = Regex("(\\d+)").findAll(this).map { it.groupValues[1].toInt() }
    private val directions = listOf(Point(0, 1), Point(-1, 1), Point(1, 1))
    val yLine by lazy { initCoordinates.maxOf { it.y } }

    data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point) = Point(x + other.x, y + other.y)
        operator fun minus(other: Point) = Point(x - other.x, y - other.y)
    }

    private fun Point.drawTo(other: Point): Sequence<Point> {
        val vector = other - this
        val direction = Point(vector.x.sign, vector.y.sign)
        val length = maxOf(vector.x.absoluteValue, vector.y.absoluteValue)
        return List(length) { direction }.runningFold(this, Point::plus).asSequence()
    }

    val initCoordinates: Set<Point> = input
        .map { it.numbers.chunked(2) { (x, y) -> Point(x, y) }.zipWithNext { a, b -> a.drawTo(b) }.flatten() }
        .flatten()
        .toSet()

    fun solution(maxLine: Int, failure: Boolean): Int {
        val coordinates = initCoordinates.toMutableSet()
        val wallSize = coordinates.size
        val start = Point(500, 0)
        while (true) {
            val nextPoint: Point = generateSequence(start) { p: Point ->
                directions.firstOrNull { (p + it) !in coordinates }?.plus(p)
            }.takeWhile { it.y <= maxLine }.last()
            when {
                nextPoint == start -> { coordinates += nextPoint; break }
                nextPoint.y == maxLine && failure -> break
            }
            coordinates += nextPoint
        }
        return coordinates.size - wallSize
    }
}

fun main() = Day14(readInputAsSequence("Day14")).run {
    check(solution(maxLine = yLine, failure = true).also(::println) == 994)
    check(solution(maxLine = yLine + 1, failure = false).also(::println) == 26283)
}