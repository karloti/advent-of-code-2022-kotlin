import kotlin.math.abs
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

class Day15(input: List<String>) {
    private val regex = Regex("(-*\\d+)")
    private val beacons = mutableSetOf<Beacon>()
    private val sensors = mutableSetOf<Sensor>()
    val IntRange.size get() = last.toLong() - first + 1
    fun numbers(s: String) = regex.findAll(s).map { it.groupValues[1].toInt() }.toList()
    fun Sensor.atLine(y1: Int) = (dist - abs(y1 - y)).takeIf { it >= 0 }?.let { IntRange(x - it, x + it) }
    fun IntRange.combine(other: IntRange) =
        if (first in other || last in other || other.first in this || other.last in this)
            (IntRange(minOf(first, other.first), maxOf(last, other.last))) else null

    data class Beacon(val x: Int, val y: Int)
    data class Sensor(val x: Int, val y: Int, val beacon: Beacon, val dist: Int = abs(beacon.x - x) + abs(beacon.y - y))

    init {
        input.map(::numbers).forEach { (sx, sy, bx, by) ->
            val beacon = Beacon(bx, by)
            beacons += beacon
            sensors += Sensor(sx, sy, beacon)
        }
    }

    fun intRangesAtLine(line: Int) = sensors
        .mapNotNull { it.atLine(line) }
        .sortedBy { it.first }
        .fold(mutableListOf<IntRange>()) { acc, intRange ->
            val last = acc.removeLastOrNull() ?: intRange
            val combine = last.combine(intRange)
            if (combine == null) {
                acc.add(last)
                acc.add(intRange)
            } else {
                acc.add(combine)
            }
            acc
        }

    fun part1(line: Int) = intRangesAtLine(line).sumOf { it.size }.minus(beacons.filter { it.y == line }.size)

    fun part2(): Long? = (sensors.minOf { it.y }..sensors.maxOf { it.y })
        .asSequence()
        .mapNotNull { line ->
            intRangesAtLine(line).takeIf { it.size == 2 && it[0].last - it[1].first == -2 }?.let { line to it }
        }
        .firstOrNull()
        ?.let { (y, intRages) -> 4000000L * (intRages[0].last + 1) + y }
}

@OptIn(ExperimentalTime::class)
fun main() {
    Day15(readInput("Day15_test")).run {
        check(part1(10) == 26L)
        check(part2() == 56000011L)
    }
    Day15(readInput("Day15")).run {
        measureTimedValue { part1(2000000) }.let(::println)
        measureTimedValue { part2() }.let(::println)
    }
}
