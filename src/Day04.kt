fun main() {
    fun Sequence<String>.splitToSets(): Sequence<List<Set<Int>>> =
        map { it.split(',').map { it.split('-').map(String::toInt).let { (a, b) -> (a..b).toSet() } } }

    fun part1(input: Sequence<String>): Int =
        input.splitToSets().count { (s1: Set<Int>, s2: Set<Int>) -> s1.containsAll(s2) || s2.containsAll(s1) }

    fun part2(input: Sequence<String>): Int =
        input.splitToSets().count { (s1: Set<Int>, s2: Set<Int>) -> s1.any(s2::contains) || s1.any(s2::contains) }

    check(part1(readInputAsSequence("Day04_test")) == 2)
    check(part2(readInputAsSequence("Day04_test")) == 4)

    println(part1(readInputAsSequence("Day04")))
    println(part2(readInputAsSequence("Day04")))
}