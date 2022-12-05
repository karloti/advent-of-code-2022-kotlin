fun main() {
    fun solution(lines: List<String>, part: Int): String {
        val initLines = lines
            .takeWhile { it.isNotEmpty() }
            .dropLast(1)
            .map { it.chunked(4).mapIndexedNotNull { i, s -> s.trim(' ', '[', ']').takeIf(String::isNotEmpty)?.let { i + 1 to it[0] } } }

        val repo: MutableMap<Int, List<Char>> = initLines
            .flatten()
            .groupingBy { it.first }
            .fold(emptyList<Char>()) { acc, (i, c) -> listOf(c) + acc }
            .toMutableMap()

        lines
            .drop(initLines.size + 2)
            .mapNotNull { Regex("""move\s(\d+)\sfrom\s(\d+)\sto\s(\d+)""").find(it)?.groupValues?.drop(1)?.map(String::toInt) }
            .onEach { (count, fromRepo, toRepo) ->
                val cargo = repo[fromRepo]!!.takeLast(count)
                repo[fromRepo] = repo[fromRepo]!!.dropLast(count)
                repo[toRepo] = repo[toRepo]!! + if (part == 1) cargo.reversed() else cargo
            }
        return repo.toSortedMap().map { it.value.last() }.joinToString("")
    }

    check(solution(readInput("Day05_test"), part = 1) == "CMZ")
    check(solution(readInput("Day05_test"), part = 2) == "MCD")

    println(solution(readInput("Day05"), part = 1))
    println(solution(readInput("Day05"), part = 2))
}