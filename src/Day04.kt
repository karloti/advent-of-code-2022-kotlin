fun main() {
    fun Sequence<String>.splitBounds() = map { it.split('-', ',').map(String::toInt) }

    fun part1(input: Sequence<String>): Int =
        input.splitBounds().count { (a, b, c, d) -> (a <= c) && (b >= d) || (a >= c) && (b >= d) }

    fun part2(input: Sequence<String>): Int =
        input.splitBounds().count { (a, b, c, d) -> a <= d && c <= b }

    check(part1(readInputAsSequence("Day04_test")) == 2)
    check(part2(readInputAsSequence("Day04_test")) == 4)

    println(part1(readInputAsSequence("Day04")))
    println(part2(readInputAsSequence("Day04")))

    check(part1(readInputAsSequence("Day04")) == 580)
    check(part2(readInputAsSequence("Day04")) == 895)
}