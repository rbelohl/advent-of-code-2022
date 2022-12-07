import java.io.File

fun cd(pwd: String, newDirectory: String) : String {
    if (newDirectory == "/")
        return "/"
    if (newDirectory == "..")
        return pwd.dropLast(1).dropLastWhile { it != '/'}
    return "$pwd$newDirectory/"
}

fun main(args: Array<String>) {
    val filename = args[0]

    val input = File(filename).readLines();

    val files = mutableMapOf<String, Int>()
    files[""] = 0 // root directory
    var pwd = "";
    input.forEach {
        val s = it.split(" ")
        if (s[0] == "$") {
            if (s[1] == "cd") {
                pwd = cd(pwd, s[2])
            }
            // do nothing for ls
        } else {
            files[pwd + s[1]] = s[0].toIntOrNull() ?: 0
        }
    }

    val sortedDirectorySizes = files
        .filter { it.value == 0 }
        .map { it.key + "/" }
        .map { directory ->
            files.filter { it.value != 0 }
                .filter { it.key.startsWith(directory) }
                .map { it.value }
                .sum()
        }
        .sorted()

    val answer1 = sortedDirectorySizes.filter { it < 100000 }.sum()

    val unusedSpace = 70000000 - sortedDirectorySizes.last()
    val neededSpace = 30000000 - unusedSpace

    val answer2 = sortedDirectorySizes.find { it >= neededSpace }

    println(answer1)
    println(answer2)
}
