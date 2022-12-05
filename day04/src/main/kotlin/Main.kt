import java.io.File

fun fullyContains(list: List<List<Int>>) : Boolean {
    val sortedList = list.sortedBy { range -> range[1] - range[0] } // smaller interval is first
    val a = sortedList[0]
    val b = sortedList[1]
    return a[0] >= b[0] && a[1] <= b[1]
}

fun overlaps(list: List<List<Int>>) : Boolean {
    val sortedList = list.sortedBy { range -> range[0] } // sort by start of interval
    val a = sortedList[0]
    val b = sortedList[1]
    return a[1] >= b[0]
}

fun main(args: Array<String>) {
    val filename = args[0]
    val ranges = File(filename)
        .readLines()
        .map { it.split(",") }
        .map { list ->
            list.map {
                it.split("-")
                    .map { s -> s.toInt() }
            }
        }

    val answer1 = ranges.count(::fullyContains)
    val answer2 = ranges.count(::overlaps)
    println(answer1)
    println(answer2)
}
