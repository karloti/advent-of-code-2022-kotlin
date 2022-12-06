fun String.indexOf(n: Int): Int = asSequence()
    .withIndex()
    .runningFold(mapOf<Char, Int>() to -1) { (map, result), (index, char) ->
        Pair(map + (char to index), (map[char]?.let { maxOf(it, result) } ?: result))
    }
    .withIndex()
    .indexOfFirst { (index, value) -> index - value.second - 1 == n }

fun main() {
    check("mjqjpqmgbljsphdztnvjfqwrcgsmlb".indexOf(4).also(::println) == 7)
    check("bvwbjplbgvbhsrlpgdmjqwftvncz".indexOf(4).also(::println) == 5)
    check("nppdvjthqldpwncqszvftbrmjlhg".indexOf(4).also(::println) == 6)
    check("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg".indexOf(4).also(::println) == 10)
    check("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw".indexOf(4).also(::println) == 11)

    check(readInputAsText("Day06").indexOf(4).also(::println) == 1909)
    check(readInputAsText("Day06").indexOf(14).also(::println) == 3380)
}