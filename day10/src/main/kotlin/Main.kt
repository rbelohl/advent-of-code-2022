import java.io.File
import kotlin.math.absoluteValue

fun main(args: Array<String>) {
    val filename = args[0]
    val instructions = File(filename)
        .readLines()
        .map { it.split(" ") }

    val registerValues = mutableListOf<Int>()
    var register = 1

    instructions.forEach { instruction ->
        registerValues.add(register)
        if (instruction[0] == "addx") {
            registerValues.add(register)
            register += instruction[1].toInt()
        }
    }

    val answer1 = registerValues
        .mapIndexed{ index, value -> (index + 1) * value }
        .filterIndexed { index, _ -> ((index + 1) % 40) == 20 }
        .sum()

    val answer2 = registerValues
        .mapIndexed { index, value ->
            val diff = value - (index % 40)
            if (diff.absoluteValue <= 1)  '#' else ' '
        }
        .chunked(40)
        .map { it.joinToString("") }
        .joinToString("\n")

    println(answer1)
    println(answer2)
}
