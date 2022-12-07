import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

// Kaloyan Karaivanov (my)
fun String.indexOf(n: Int): Int = asSequence()
    .withIndex()
    .runningFold(mutableMapOf<Char, Int>() to -1) { (m, r), (i, c) -> m to (m.put(c, i)?.coerceAtLeast(r) ?: r) }
    .withIndex()
    .indexOfFirst { (index, value) -> index - value.second - 1 == n }

// Sergei Petunin
fun solution(input: String, length: Int): Int {
    var distinctCount = 0
    val charCounts = IntArray(256)
    return 1 + input.indices.first {
        if (it >= length) {
            when (--charCounts[input[it - length].code]) {
                0 -> distinctCount--
                1 -> distinctCount++
            }
        }
        when (++charCounts[input[it].code]) {
            1 -> distinctCount++
            2 -> distinctCount--
        }
        distinctCount == length
    }
}

// Alexander af Trolle
fun String.startMessageIndexLinear(size: Int): Int {
    val dublicateIndexMap = mutableMapOf<Char, Int>()
    var dublicateIndex = 0
    var index = 0
    return indexOfFirst { char ->
        val lastSeen = dublicateIndexMap.put(char, index) ?: 0
        dublicateIndex = dublicateIndex.coerceAtLeast((lastSeen))
        index++ - dublicateIndex >= size + 1

    }
}

@OptIn(ExperimentalTime::class)
fun main() {
    val input = ('a'..'y').joinToString("").repeat(1_000_000) + "z"
    print("Sergei Petunin = ")
    measureTimedValue { solution(input, 26) }.let(::println)

    print("Alexander af Trolle = ")
    measureTimedValue { input.startMessageIndexLinear(26) }.let(::println)

    print("Kaloyan Karaivanov = ")
    measureTimedValue { input.indexOf(26) }.let(::println)

    print("windowed-native = ")
    measureTimedValue { input.windowed(26).indexOfFirst { it.toSet().size == 26 } + 26 }.let(::println)

    check(readInputAsText("Day06").indexOf(4) == 1909)
    check(readInputAsText("Day06").indexOf(14) == 3380)
}