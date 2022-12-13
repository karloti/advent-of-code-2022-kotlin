sealed interface PacketData : Comparable<PacketData>

data class ListValue(val values: List<PacketData>) : PacketData {
    override fun toString() = values.toString()
    override operator fun compareTo(other: PacketData): Int = when (other) {
        is ListValue -> values.zip(other.values).asSequence()
            .map { (a, b) -> a.compareTo(b) }
            .firstOrNull { it != 0 } ?: values.size.compareTo(other.values.size)
        else -> compareTo(ListValue(listOf(other)))
    }
}

data class IntValue(val value: Int) : PacketData {
    override fun toString() = value.toString()
    override operator fun compareTo(other: PacketData ): Int = when (other) {
        is IntValue -> value.compareTo(other.value)
        is ListValue -> ListValue(listOf(this)).compareTo(other)
    }
}

fun String.parsePacketData() = ArrayDeque(toList()).parsePacketData()

fun ArrayDeque<Char>.parsePacketData(): PacketData {
    if (first() != '[') return IntValue(joinToString("").toInt())
    removeFirst()
    val values = mutableListOf<PacketData>()
    while (size > 1) {
        val text = ArrayDeque<Char>()
        var braceCounter = 0
        while (true) {
            val character = removeFirst()
            when (character) {
                '[' -> braceCounter++
                ']' -> if (--braceCounter < 0) break
                ',' -> if (braceCounter == 0) break
            }
            text.add(character)
        }
        values.add(text.parsePacketData())
    }
    return ListValue(values)
}

fun main() {
    val test = "[1,2,[1]]".parsePacketData().let(::println)

//    val input = readInput("Day13_test")
//    input
//        .filter(String::isNotBlank)
//        .map(String::parsePacketData)
//        .chunked(2)
//        .mapIndexed { i, it -> if (it[0] < it[1]) i + 1 else 0 }
//        .sum()
//        .let(::println)
//
//    val two = "[[2]]".parsePacketData()
//    val six = "[[6]]".parsePacketData()
//    input
//        .filter(String::isNotBlank)
//        .map(String::parsePacketData)
//        .plus(listOf(two, six))
//        .sorted()
//        .run { (indexOf(two) + 1) * indexOf(six) + 1 }
//        .let(::println)
}
