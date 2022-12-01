fun main() {
    fun part1(input: String): Int = input
        .split("\n\n")
        .maxOf { it.split("\n").sumOf(String::toInt) }

    fun part2(input: String): Int = input
        .split("\n\n")
        .map { it.split("\n").sumOf(String::toInt) }
        .sortedDescending()
        .take(3)
        .sum()

//    val testInput = readInputAsText("Day01_test")
//    check(part1(testInput) == 24000)

    val input = readInputAsText("Day01")
    println(part1(input))
    println(part2(input))
}