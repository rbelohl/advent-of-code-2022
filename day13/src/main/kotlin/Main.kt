import java.io.File

class Node(val parent: Node?, val value: Int?) {
    val children: MutableList<Node> = mutableListOf()

    override fun toString(): String {
        val sb = StringBuilder()
        if (value != null) {
            sb.append(value)
            sb.append(',')
        } else {
            sb.append('[')
            children.forEach { sb.append(it) }
            sb.append(']')
        }
        return sb.toString()
    }
}

fun compare(left: Node, right: Node) : Int {
    // println("Comparing $left to $right")
    if (left.value != null && right.value != null) {
        // compare two integers
        return left.value.compareTo(right.value)
    }
    if (right.value != null) {
        // convert right integer to list
        val tmpRight = Node(right.parent, null).apply { children.add(Node(this, right.value)) }
        return compare(left, tmpRight)
    }
    if (left.value != null) {
        // convert left integer to list
        val tmpLeft = Node(left.parent, null).apply { children.add(Node(this, left.value)) }
        return compare(tmpLeft, right)
    }

    var i = 0
    while (true) {
        val l = left.children.getOrNull(i)
        val r = right.children.getOrNull(i)
        if (l == null && r == null) return 0 // both lists ran out of items
        if (l == null) return -1 // left ran out of items
        if (r == null) return 1 // right ran out of items

        val cmp = compare(left.children[i], right.children[i])
        if (cmp != 0) return cmp
        i++
    }
}

fun parse(input: String) : Node {
    val root = Node(null, null)
    var current = root
    var tmpIntValue = 0
    var parsingInt = false

    input.forEach { c ->
        if (c == '[') {
            val newNode = Node(current, null)
            current.children.add(newNode)
            current = newNode
        } else if (c ==']') {
            if (parsingInt) {
                current.children.add(Node(current, tmpIntValue))
                parsingInt = false
            }
            current = current.parent!!
        } else if (c == ',') {
            if (parsingInt) {
                current.children.add(Node(current, tmpIntValue))
            }
            parsingInt = false
        } else if (c.isDigit()) {
            if (!parsingInt) {
                tmpIntValue = 0
            }
            parsingInt = true
            tmpIntValue = tmpIntValue * 10 + c.digitToInt()
        }
    }
    return root.children[0]
}

fun main(args: Array<String>) {
    val filename = args[0]
    val input = File(filename).readLines()
        .filter { it.isNotBlank() }
        .map { parse(it) }

    val answer1 = input.chunked(2)
        .mapIndexed { i, value -> Pair(i + 1, value) }
        .filter { compare(it.second[0], it.second[1]) < 0 }
        .sumOf { it.first }

    val divider1 = parse("[[2]]")
    val divider2 = parse("[[6]]")
    val inputPart2 = input + divider1 + divider2

    val sortedInput = inputPart2.sortedWith(::compare)
    val answer2 = (sortedInput.indexOf(divider1) + 1) * (sortedInput.indexOf(divider2) + 1)

    println(answer1)
    println(answer2)
}