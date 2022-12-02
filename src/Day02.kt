fun main() {
    fun part1(input: List<String>) = input
        .map { (it[0] - 'A') to (it[2] - 'X') }
        .sumOf { (a, b) -> 3 * (4 + b - a).rem(3) + b + 1 }

    fun part2(input: List<String>) = input
        .map { (it[0] - 'A') to (it[2] - 'X') }
        .sumOf { (a, b) -> 3 * b + (a + b + 2) % 3 + 1 }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}