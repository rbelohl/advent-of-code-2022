import java.io.File

fun wrap(i: Long, length: Int) = (((i % length) + length) % length).toInt()

fun decrypt(input: List<Int>, key: Long, repeats: Int) : Long {
    val input2 = input.mapIndexed { index, number -> Pair(index, number * key) }
    val output = input2.toMutableList()

    repeat(repeats) {
        input2.forEach {
            val number = it.second
            val index = output.indexOf(it)
            output.removeAt(index)
            val newIndex = wrap(index + number, output.size)
            output.add(newIndex, it)
        }
    }
    val zeroIndex = output.indexOfFirst { it.second.toInt() == 0 }
    return listOf<Long>(1000, 2000, 3000)
        .map { wrap(it + zeroIndex, output.size) }
        .sumOf { output[it].second }
}

fun main(args: Array<String>) {
    val filename = args[0]
    val input = File(filename).readLines().map { it.toInt() }

    val answer1 = decrypt(input, 1, 1)
    val answer2 = decrypt(input, 811589153, 10)

    println(answer1)
    println(answer2)
}
