import java.io.File

fun findCommonElement(s: String) : Char {
    val mid = s.length / 2
    val firstHalf = s.substring(0, mid).toSet()
    val secondHalf = s.substring(mid).toSet()
    return firstHalf.intersect(secondHalf).first()
}

fun getBadge(bags: List<String>) : Char {
    return bags
        .map{ it.toSet()}
        .reduce { acc, s -> acc.intersect(s) }
        .first()
}

fun getPriority(c: Char) : Int {
    if (c in 'a'..'z') return c - 'a' + 1
    if (c in 'A'..'Z') return c - 'A' + 27
    return -1 // error
}

fun main(args: Array<String>) {
    val filename = args[0]
    val bags = File(filename)
        .readLines()
    val answer1 = bags
        .map(::findCommonElement)
        .map(::getPriority)
        .sum()
    val answer2 = bags
        .chunked(3)
        .map(::getBadge)
        .map(::getPriority)
        .sum()
    println(answer1)
    println(answer2)
}

