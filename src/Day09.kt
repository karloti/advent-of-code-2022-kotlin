import kotlin.math.absoluteValue
import kotlin.math.sign

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point): Point = Point(x + other.x, y + other.y)
    infix fun moveTo(d: Point): Point = takeIf { (x - d.x).absoluteValue <= 1 && (y - d.y).absoluteValue <= 1 }
        ?: Point(x + (d.x - x).sign, y + (d.y - y).sign)
}

fun String.toPoints() = split(' ')
    .let { (direction, distance) ->
        List(distance.toInt()) {
            when (direction[0]) {
                'U' -> Point(0, 1)
                'D' -> Point(0, -1)
                'L' -> Point(-1, 0)
                'R' -> Point(1, 0)
                else -> throw IllegalArgumentException()
            }
        }
    }

fun List<String>.solution(size: Int): Int {
    val rope = MutableList(size) { Point(0, 0) }
    val tale: MutableSet<Point> = mutableSetOf()
    forEach {
        val headStepByStep: List<Point> = it.toPoints().runningFold(rope[0], Point::plus).drop(1)
        headStepByStep.onEach { head: Point ->
            rope[0] = head
            rope.indices.zipWithNext { i1, i2 -> rope[i2] = rope[i2].moveTo(rope[i1]) }
            tale += rope.last()
        }
    }
    return tale.size
}

fun main() {
    val input: List<String> = readInput("Day09")
    check(input.solution(2).also(::println) == 5779)
    check(input.solution(10).also(::println) == 2331)
}