import java.io.File

fun getGameResult(p : Pair<Int, Int>) : Int {
    val x = ((p.second - p.first) % 3 + 4) % 3
    return x * 3
}

fun getPoints(p: Pair<Int, Int>) : Int = getGameResult(p) + p.second + 1;

fun whatToPlay(p: Pair<Int, Int>) : Int {
    val x = (p.second - 1)
    return ((p.first + x) % 3 + 3) % 3
}


fun main(args: Array<String>) {
    val filename = args[0]
    val pairs = File(filename)
        .readLines()
        .map { Pair(it[0] - 'A', it[2] - 'X') }

    val answer1 = pairs
        .map(::getPoints)
        .sum()
    val answer2 = pairs
        .map{ Pair(it.first, whatToPlay(it))}
        .map(::getPoints)
        .sum()
    println(answer1)
    println(answer2)
}
