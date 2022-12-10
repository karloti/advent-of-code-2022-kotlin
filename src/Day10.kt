import Instructions.*

sealed interface Instructions {
    val value: Int
    val cycle: Int
    val total: Int

    data class Noop(
        override val value: Int = 0,
        override val cycle: Int = cycle_++,
        override val total: Int = total_,
    ) : Instructions

    data class Addx(
        override val value: Int,
        override val cycle: Int = cycle_++,
        override val total: Int = total_.apply { total_ += value }
    ) : Instructions

    companion object {
        private var cycle_ = 1
        private var total_ = 1
    }
}

fun List<String>.toInstructions(): List<Instructions> = buildList<Instructions> {
    this@toInstructions.forEach {
        if (it[0] == 'n') {
            add(Noop())
        } else {
            add(Noop())
            add(Addx(it.split(' ')[1].toInt()))
        }
    }
}

fun List<Instructions>.part1() = filter { (it.cycle - 20) % 40 == 0 }.take(6).sumOf { it.cycle * it.total }
fun List<Instructions>.part2() = map { if ((it.cycle - 1) % 40 - it.total in -1..1) "{}" else "  " }
    .chunked(40).joinToString("\n") { it.joinToString("") }

fun main() {
    val instructions: List<Instructions> = readInput("Day10").toInstructions()
    println(instructions.part1())
    println()
    println(instructions.part2())
}