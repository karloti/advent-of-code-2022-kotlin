fun main() {
    fun String.splitBounds(): Sequence<List<Int>> =
        Regex("""(\d+).(\d+).(\d+).(\d+)""").findAll(this).map { it.groupValues.drop(1).map(String::toInt) }

    fun part1(input: String): Int =
        input.splitBounds().count { (a, b, c, d) -> (a in c..d && b in c..d) || (c in a..b && d in a..b) }

    fun part2(input: String): Int =
        input.splitBounds().count { (a, b, c, d) -> a in c..d || b in c..d || c in a..b || d in a..b }

    val inputTest = readInputAsText("Day04_test")
    check(part1(inputTest) == 2)
    check(part2(inputTest) == 4)

    val input = readInputAsText("Day04")
    println(part1(input))
    println(part2(input))

    check(part1(input) == 580)
    check(part2(input) == 895)
}