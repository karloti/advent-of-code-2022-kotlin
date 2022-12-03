fun main() {
    val code = (('a'..'z') + ('A'..'Z')).withIndex().associate { (i, c) -> c to i + 1 }

    fun part1(input: List<String>): Int = input
        .map { s -> s.chunked(s.length / 2, CharSequence::toSet) }
        .map { it.reduce(Set<Char>::intersect).single() }
        .sumOf { code[it]!! }

    fun part2(input: List<String>): Int = input
        .chunked(3) { it.map(String::toSet) }
        .map { it.reduce(Set<Char>::intersect).single() }
        .sumOf { code[it]!! }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}