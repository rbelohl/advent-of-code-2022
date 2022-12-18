import java.io.File
import java.util.LinkedList

operator fun Triple<Int, Int, Int>.plus(other: Triple<Int, Int, Int>) : Triple<Int, Int, Int> {
    return Triple(this.first + other.first, this.second + other.second, this.third + other.third)
}

fun neighbors(cube: Triple<Int, Int, Int>): List<Triple<Int, Int, Int>> {
    val sides = listOf(
        Triple(1, 0, 0),
        Triple(-1, 0, 0),
        Triple(0, 1, 0),
        Triple(0, -1, 0),
        Triple(0, 0, 1),
        Triple(0, 0, -1),
    )
    return sides.map { cube + it }
}

fun bfs(start: Triple<Int, Int, Int>, cubes: Set<Triple<Int, Int, Int>>) : Int {
    val queue = LinkedList<Triple<Int, Int, Int>>()
    var sides = 0
    val visited = mutableSetOf(start)
    queue.add(start)
    while (queue.isNotEmpty()) {
        val current = queue.removeFirst()
        val min = -1
        val max = 22

        neighbors(current).forEach { neighbor ->
            if (neighbor.first < min || neighbor.first > max || neighbor.second < min || neighbor.second > max || neighbor.third < min || neighbor.third > max) return@forEach
            if (!visited.contains(neighbor)) {
                if (cubes.contains(neighbor)) {
                    sides++
                } else {
                    queue.add(neighbor)
                    visited.add(neighbor)
                }
            }
        }
    }
    return sides
}

fun main(args: Array<String>) {
    val filename = args[0]
    val input = File(filename).readLines()
        .map { it.split(",") }
        .map { s -> s.map { it.toInt() } }
        .map { Triple(it[0], it[1], it[2]) }

    val cubes = input.toSet()

    val answer1 = cubes.fold(0) { acc, cube -> acc + neighbors(cube).count { !cubes.contains(it) } }
    val answer2 = bfs(Triple(0,0,0), cubes)

    println(answer1)
    println(answer2)
}
