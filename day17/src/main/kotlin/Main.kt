import java.io.File
import kotlin.math.max

fun <T> infiniteRepeat(sequence: Sequence<T>) = sequence { while (true) yieldAll(sequence) }

fun collision(cave: List<CharArray>, rock: List<String>, x: Int, y: Int) : Boolean {
    for (yy in rock.indices) {
        for (xx in rock[yy].indices) {
            if (rock[yy][xx] == '#') {
                if (cave[y + yy][x + xx] == '#') {
                    return true
                }
            }
        }
    }
    return false
}

fun stop(cave: List<CharArray>, rock: List<String>, x: Int, y: Int) {
    for (yy in rock.indices) {
        for (xx in rock[yy].indices) {
            if (rock[yy][xx] == '#') {
                cave[y + yy][x + xx] = '#'
            }
        }
    }
}

fun printCave(cave: List<CharArray>, rock: List<String>, x: Int, y: Int) {
    val output = cave.map { it.copyOf() }
    for (yy in rock.indices) {
        for (xx in rock[yy].indices) {
            if (rock[yy][xx] == '#') {
                output[y + yy][x + xx] = '@'
            }
        }
    }
    output.reversed().forEach(::println)
}


fun main(args: Array<String>) {
    val filename = args[0]
    val input = File(filename).readLines().first()

    val shapes = listOf(
        listOf("####"),
        listOf(".#.", "###", ".#."),
        listOf("###", "..#", "..#"),
        listOf("#", "#", "#", "#"),
        listOf("##", "##")
    )

    val infiniteInput = infiniteRepeat(input.asSequence()).iterator()

    val caveWidth = 7
    fun newRow() = CharArray(caveWidth) { '.' }

    val cave = MutableList(7) { newRow() }

    val numRocks = 2022
    val rocks = infiniteRepeat(shapes.asSequence()).take(numRocks)

    var highest = 0

    rocks.forEach { rock ->
        val rockWidth = rock[0].length
        var x = 2
        var y = highest + 3

        while (true) {
            val pushDirection = infiniteInput.next()
            if (pushDirection == '<') {
                if (x > 0 && !collision(cave, rock, x - 1, y)) {
                    x--
                }
            } else if (pushDirection == '>') {
                if (x < caveWidth - rockWidth && !collision(cave, rock, x + 1, y)) {
                    x++
                }
            }

            if ((y - 1) < 0 || collision(cave, rock, x, y - 1))  {
                stop(cave, rock, x, y)
                highest = max(y + rock.size, highest)
                while (cave.size < highest + 7) {
                    cave.add(newRow())
                }
                break;
            }
            y--
        }
    }
    println(highest)

}

