import java.io.File

fun makeStep(containers: List<String>, step: List<Int>, reversed: Boolean) : List<String> {
    val newContainers = containers.toMutableList()
    val amount = step[0]
    val from = step[1] - 1
    val to = step[2] - 1

    val length = containers[from].length
    val crates = containers[from].substring(length - amount)
    newContainers[from] = containers[from].substring(0, length - amount)
    newContainers[to] += if (reversed) crates.reversed() else crates
    return newContainers.toList()
}

fun makeStepPart1(containers: List<String>, step: List<Int>) : List<String> = makeStep(containers, step, true)
fun makeStepPart2(containers: List<String>, step: List<Int>) : List<String> = makeStep(containers, step, false)

fun main(args: Array<String>) {
    val filename = args[0]

    val lines = File(filename).readLines()
    val starting = lines.takeWhile { it.isNotBlank() }
        .reversed()
        .drop(1)
        .map { it.filterIndexed { index, _ -> (index - 1) % 4 == 0} }
    val tmpContainers = MutableList(starting.first().length) { "" }
    starting.forEach { line ->
        line.forEachIndexed { index, c ->
            tmpContainers[index] += c.toString()
        }
    }

    val containers = tmpContainers.map(String::trim).toList()

    val instructions = lines.takeLastWhile { it.isNotBlank() }
        .map { it.split(" ") }
        .map { listOf(it[1].toInt(), it[3].toInt(), it[5].toInt()) }

    val answer1 = instructions.fold(containers, ::makeStepPart1)
        .map { it.last() }
        .joinToString("")
    val answer2 = instructions.fold(containers, ::makeStepPart2)
        .map { it.last() }
        .joinToString("")

    println(answer1)
    println(answer2)
}
