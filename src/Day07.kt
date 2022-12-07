import Command.*
import Content.Directory
import Content.File
import java.util.*

enum class Errors {
    UNKNOWN_COMMAND_LINE,
    DIRECTORY_NOT_FOUND,
}

enum class Command(val regex: Regex) {
    DIRECTORY("dir (.+)".toRegex()),
    FILE("(\\d+) (.+)".toRegex()),
    CD("\\$ cd (.+)".toRegex()),
    LS("\\$ ls".toRegex()),
    ;

    companion object {
        fun toCommand(s: String) = values().firstOrNull { it.regex.matches(s) }
    }
}

sealed class Content private constructor(
    var parent: Content? = null,
    open val name: String = "",
) {
    data class Directory(
        override val name: String,
        var size: Long = 0L,
        val children: MutableList<Content> = mutableListOf()
    ) : Content() {
        fun add(content: Content) {
            content.parent = this
            children.add(content)
            var current: Directory? = this
            if (content is File)
                while (current != null) {
                    current.size += content.size
                    current = current.parent as Directory?
                }
        }
    }

    data class File(
        override val name: String,
        val size: Long
    ) : Content()
}

fun root(input: List<String>): Directory {
    val root = Directory("/")
    var currentDirectory: Directory = root
    input.forEach { line ->
        val command = Command.toCommand(line) ?: throw Exception(Errors.UNKNOWN_COMMAND_LINE.name + ": $line")
        val args = command.regex.find(line)!!.groupValues.drop(1)
        when (command) {
            DIRECTORY -> currentDirectory.add(Directory(args.single()))
            FILE -> currentDirectory.add(File(args[1], args[0].toLong()))
            CD -> when (val cdPath = args.single()) {
                ".." -> currentDirectory = currentDirectory.parent as Directory
                "/" -> currentDirectory = root
                else -> currentDirectory = currentDirectory.children.firstOrNull { content ->
                    cdPath == when (content) {
                        is Directory -> content.name
                        is File -> null
                    }
                } as Directory? ?: throw Exception(Errors.DIRECTORY_NOT_FOUND.name + ": $line")
            }

            LS -> {}
        }
    }
    return root
}

fun part1(root: Directory): Long {
    var result = 0L
    val queue = ArrayDeque<Directory>().apply { add(root) }
    while (queue.isNotEmpty()) {
        val element = queue.removeFirst()
        if (element.size <= 100_000) result += element.size
        element.children.forEach { content -> if (content is Directory) queue.addLast(content) }
    }
    return result
}

fun part2(root: Directory): Long? {
    val needSpace = 30_000_000L + root.size - 70_000_000L
    val allDirectory = mutableListOf<Directory>()
    val queue = ArrayDeque<Directory>().apply { add(root) }
    while (queue.isNotEmpty()) {
        val element = queue.removeFirst()
        allDirectory += element
        element.children.forEach { content -> if (content is Directory) queue.addLast(content) }
    }
    allDirectory.sortBy { it.size }
    return allDirectory.firstOrNull { it.size > needSpace }?.size
}

fun main() {
    val rootTest = root(readInput("Day07_test"))
    check(part1(rootTest) == 95437L)
    check(part2(rootTest) == 24933642L)

    val root = root(readInput("Day07"))
    check(part1(root) == 1348005L)
    check(part2(root) == 12785886L)
}