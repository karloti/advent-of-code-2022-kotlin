import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

fun String.indexOf(n: Int): Int = asSequence()
    .withIndex()
    .runningFold(mutableMapOf<Char, Int>() to -1) { (m, r), (i, c) ->
        m to (m.put(c, i)?.coerceAtLeast(r) ?: r)
    }
    .withIndex()
    .indexOfFirst { (index, value) -> index - value.second - 1 == n }


@OptIn(ExperimentalTime::class)
fun main() {
    val s = ('a'..'y').joinToString("").repeat(1_000_000) + "z"
    print("runningFold = ")
    measureTimedValue { s.indexOf(26) }.let(::println)

    print("windowed = ")
    measureTimedValue { s.windowed(26).indexOfFirst { it.toSet().size == 26 } + 26 }.let(::println)

    check(readInputAsText("Day06").indexOf(4) == 1909)
    check(readInputAsText("Day06").indexOf(14) == 3380)
}