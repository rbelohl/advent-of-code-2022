import java.io.File

val operations = mapOf<String, (Long, Long) -> Long>(
    "+" to { a, b -> a + b},
    "-" to { a, b -> a - b},
    "/" to { a, b -> a / b},
    "*" to { a, b -> a * b}
)

val solveForLeft = mapOf<String, (Long, Long) -> Long>(
    "+" to { right, desired -> desired - right},
    "-" to { right, desired -> desired + right},
    "/" to { right, desired -> desired * right},
    "*" to { right, desired -> desired / right},
)

val solveForRight = mapOf<String, (Long, Long) -> Long>(
    "+" to { left, desired -> desired - left},
    "-" to { left, desired -> left - desired},
    "/" to { left, desired -> left / desired},
    "*" to { left, desired -> desired / left},
)


fun getValue(monkeys: Map<String, String>, name: String): Long {
    val s = monkeys[name]!!.split(' ')
    if (s.size == 1) {
        return s[0].toLong()
    }
    val left = getValue(monkeys, s[0])
    val right = getValue(monkeys, s[2])
    val op = operations[s[1]]!!
    return op(left, right)
}

fun getValue2(monkeys: Map<String, String>, name: String): Long? {
    if (name == "humn") {
        return null
    }
    val s = monkeys[name]!!.split(' ')
    if (s.size == 1) {
        return s[0].toLong()
    }
    val left = getValue2(monkeys, s[0]) ?: return null
    val right = getValue2(monkeys, s[2]) ?: return null
    val op = operations[s[1]]!!
    return op(left, right)
}

fun getValueInverse(monkeys: Map<String, String>, name: String, desiredValue: Long) : Long {
    if (name == "humn") {
        return desiredValue
    }
    val s = monkeys[name]!!.split(' ')
    val left = getValue2(monkeys, s[0])
    val right = getValue2(monkeys, s[2])
    if (left == null) {
        val op = solveForLeft[s[1]]!!
        val x = op(right!!, desiredValue)
        return getValueInverse(monkeys, s[0], x)
    }
    if (right == null) {
        val op = solveForRight[s[1]]!!
        val x = op(left, desiredValue)
        return getValueInverse(monkeys, s[2], x)
    }
    throw Exception("wtf")
}


fun main(args: Array<String>) {
    val filename = args[0]
    val input = File(filename).readLines()
        .map { it.split(':') }
        .associate { it[0] to it[1].trim() }

    val answer1 = getValue(input, "root")

    val root = input["root"]!!.split(' ')
    val left = root[0]
    val right = root[2]

    val valueLeft = getValue2(input, left)
    val valueRight = getValue2(input, right)

    val answer2 = if (valueLeft == null) {
        getValueInverse(input, left, valueRight!!)
    } else {
        getValueInverse(input, right, valueLeft)
    }

    println(answer1)
    println(answer2)
}
