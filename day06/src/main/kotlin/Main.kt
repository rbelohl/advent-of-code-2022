import java.io.File

fun findDistinctPacket(input: String, packetSize: Int) : Int {
    val index = input
        .windowed(packetSize)
        .indexOfFirst { s -> s.toHashSet().size == packetSize }
    return index + packetSize
}

fun main(args: Array<String>) {
    val filename = args[0]

    val input = File(filename).readLines().first();
    val answer1 = findDistinctPacket(input, 4)
    val answer2 = findDistinctPacket(input, 14)

    println(answer1)
    println(answer2)
}

