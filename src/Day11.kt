import java.lang.IllegalArgumentException

data class Monkey(
    val number: Int,
    val worryLevel: MutableList<Long>,
    val oppSign: Char,
    val oppNumber: Int?,
    val div: Int,
    val ifTrueMonkeyNum: Int,
    val ifFalseMonkeyNum: Int,
    var inspectCount: Long = 0,
)

fun List<String>.toMonkey(): Monkey {
    val number = get(0).drop(7).dropLast(1).toInt()
    val (items, opp, div, t, f) = drop(1)
    val worryLevel = items.drop(18).split(", ").map(String::toLong).toMutableList()
    val oppSign = opp.drop(23)[0]
    val oppNumber = opp.drop(25).toIntOrNull()
    val divNumber = div.drop(21).toInt()
    val ifTrueMonkeyNum = t.drop(29).toInt()
    val ifFalseMonkeyNum = f.drop(30).toInt()
    return Monkey(number, worryLevel, oppSign, oppNumber, divNumber, ifTrueMonkeyNum, ifFalseMonkeyNum)
}

fun MutableList<Monkey>.solution(rep: Int, worryDivider: Int): Long {
    val mod = map { it.div }.reduce(Int::times)
    repeat(rep) {
        forEach { monkey: Monkey ->
            repeat(monkey.worryLevel.size) {
                monkey.inspectCount++
                val worry = monkey.worryLevel.removeFirst()
                val oppNumber = monkey.oppNumber?.toLong() ?: worry
                val result = when (monkey.oppSign) {
                    '+' -> worry + oppNumber
                    '-' -> worry - oppNumber
                    '*' -> worry * oppNumber
                    '/' -> worry / oppNumber
                    else -> throw IllegalArgumentException()
                } / worryDivider % mod
                if (result.rem(monkey.div) == 0L) {
                    get(monkey.ifTrueMonkeyNum).apply { worryLevel += result }
                } else {
                    get(monkey.ifFalseMonkeyNum).apply { worryLevel += result }
                }
            }
        }
    }
    return sortedByDescending { it.inspectCount }.take(2).let { (a, b) -> a.inspectCount * b.inspectCount }
}

fun main() {
    check(readInput("Day11_test").chunked(7).map { it.toMonkey() }.toMutableList().solution(20, 3) == 10605L)
    check(readInput("Day11_test").chunked(7).map { it.toMonkey() }.toMutableList().solution(10000, 1) == 2713310158L)

    readInput("Day11").chunked(7).map { it.toMonkey() }.toMutableList().solution(20, 3).let(::println)
    readInput("Day11").chunked(7).map { it.toMonkey() }.toMutableList().solution(10000, 1).let(::println)
}