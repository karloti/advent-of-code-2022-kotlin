fun part1(a: Array<IntArray>): Int {
    val n = a.size
    val m = a[0].size
    val b = Array(n) { BooleanArray(m) }
    var hIndex: Int
    var h1: Int
    var h2: Int
    for (i in 0 until n) {
        hIndex = 0
        h1 = -1
        for (j in 0 until m) {
            if (a[i][j] > h1) {
                h1 = a[i][j]
                hIndex = j
                b[i][j] = true
            } else if (h1 == 9) break
        }
        h2 = -1
        for (j in m - 1 downTo hIndex) {
            if (a[i][j] > h2) {
                if (h1 == h2) break
                h2 = a[i][j]
                b[i][j] = true
            }
        }
    }
    for (j in 0 until m) {
        hIndex = 0
        h1 = -1
        for (i in 0 until n) {
            if (a[i][j] > h1) {
                h1 = a[i][j]
                hIndex = i
                b[i][j] = true
            } else if (h1 == 9) break
        }
        h2 = -1
        for (i in n - 1 downTo hIndex) {
            if (a[i][j] > h2) {
                if (h1 == h2) break
                h2 = a[i][j]
                b[i][j] = true
            }
        }
    }
    var count = 2 * m + 2 * n - 4
    for (i in 1 until n - 1)
        for (j in 1 until m - 1)
            if (b[i][j]) count++
    return count
}
fun part2(a: Array<IntArray>): Int {
    val n = a.size
    val m = a[0].size
    var result = 0
    for (i in 0 until n) {
        for (j in 0 until m) {
            var d1 = j
            for (k in j - 1 downTo 0) {
                if (a[i][k] >= a[i][j]) {
                    d1 = j - k
                    break
                }
            }
            var d2 = m - j - 1
            for (k in j + 1 until m) {
                if (a[i][k] >= a[i][j]) {
                    d2 = k - j
                    break
                }
            }
            var d3 = i
            for (k in i - 1 downTo 0) {
                if (a[k][j] >= a[i][j]) {
                    d3 = i - k
                    break
                }
            }
            var d4 = n - i - 1
            for (k in i + 1 until n) {
                if (a[k][j] >= a[i][j]) {
                    d4 = k - i
                    break
                }
            }
            val f = d1 * d2 * d3 * d4
            if (f > result) result = f
        }
    }
    return result
}

fun main() {
    val aTest = readInput("Day08_test").map { it.map(Char::digitToInt).toIntArray() }.toTypedArray()
    check(part1(aTest).also(::println) == 21)
    check(part2(aTest).also(::println) == 8)

    val a = readInput("Day08").map { it.map(Char::digitToInt).toIntArray() }.toTypedArray()
    check(part1(a).also(::println) == 1719)
    check(part2(a).also(::println) == 590824)
}