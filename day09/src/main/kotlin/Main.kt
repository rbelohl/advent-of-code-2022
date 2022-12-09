import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.sign

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>) : Pair<Int, Int> {
    return Pair(this.first + other.first, this.second + other.second)
}

operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>) : Pair<Int, Int> {
    return Pair(this.first - other.first, this.second - other.second)
}

fun moveLink(head: Pair<Int, Int>, tail: Pair<Int, Int>): Pair<Int, Int> {
    val diff = head - tail
    if (diff.first.absoluteValue == 2 || diff.second.absoluteValue == 2) {
        return tail + Pair(diff.first.sign, diff.second.sign)
    }
    return tail
}

fun moveRope(rope: MutableList<Pair<Int, Int>>, direction: Pair<Int, Int>) {
    rope[0] += direction
    for (i in 1 until rope.size) {
        rope[i] = moveLink(rope[i - 1], rope[i])
    }
}

fun simulateRope(movements: List<Pair<String, Int>>, ropeLength: Int) : Int {
    val directions = mapOf(
        "U" to Pair( 0,  1),
        "D" to Pair( 0, -1),
        "L" to Pair(-1,  0),
        "R" to Pair( 1,  0)
    )
    val rope = MutableList(ropeLength) { Pair(0, 0) }
    val visitedPositions = mutableSetOf(rope.last())

    movements.forEach {
        for (i in 0 until it.second) {
            val direction = directions[it.first]!!
            moveRope(rope, direction)
            visitedPositions.add(rope.last())
        }
    }
    return visitedPositions.size
}

fun main(args: Array<String>) {
    val filename = args[0]
    val movements = File(filename)
        .readLines()
        .map {
            val s = it.split(" ")
            Pair(s[0], s[1].toInt())
        }

    val answer1 = simulateRope(movements, 2)
    val answer2 = simulateRope(movements, 10)

    println(answer1)
    println(answer2)
}
