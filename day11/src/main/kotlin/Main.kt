class Monkey(startingItems: List<Long>, val operation: (old: Long) -> Long, val test: (item: Long) -> Boolean, val monkeyIfTrue: Int, val monkeyIfFalse: Int) {
    val items: MutableList<Long> = startingItems.toMutableList()
    var itemsIspected = 0
    fun copy() = Monkey(items.toList(), operation, test, monkeyIfTrue, monkeyIfFalse)
}

fun monkeyBusiness(monkeys: List<Monkey>) = monkeys
    .map { it.itemsIspected.toLong() }
    .sorted()
    .takeLast(2)
    .reduce { acc, i -> acc * i}

fun main(args: Array<String>) {
    val startingMonkeys = listOf(
        Monkey(listOf(72, 64, 51, 57, 93, 97, 68), { old -> old * 19 }, { item->item % 17 == 0.toLong() }, 4, 7),
        Monkey(listOf(62), { old -> old * 11 }, { item->item % 3 == 0.toLong() }, 3, 2),
        Monkey(listOf(57, 94, 69, 79, 72), { old -> old + 6 }, { item->item % 19 == 0.toLong() }, 0, 4),
        Monkey(listOf(80, 64, 92, 93, 64, 56), { old -> old + 5 }, { item->item % 7 == 0.toLong() }, 2, 0),
        Monkey(listOf(70, 88, 95, 99, 78, 72, 65, 94), { old -> old + 7 }, { item->item % 2 == 0.toLong() }, 7, 5),
        Monkey(listOf(57, 95, 81, 61), { old -> old * old }, { item->item % 5 == 0.toLong() }, 1, 6),
        Monkey(listOf(79, 99), { old -> old + 2 }, { item->item % 11 == 0.toLong() }, 3, 1),
        Monkey(listOf(68, 98, 62), { old -> old + 3 }, { item->item % 13 == 0.toLong() }, 5, 6)
    )
    /*
    val startingMonkeys = listOf(
        Monkey(listOf(79,98), { old -> old * 19 }, { item->item % 23 == 0.toLong() }, 2, 3),
        Monkey(listOf(54,65,75,74), { old -> old + 6 }, { item->item % 19 == 0.toLong() }, 2, 0),
        Monkey(listOf(79,60,97), { old -> old * old }, { item->item % 13 == 0.toLong() }, 1, 3),
        Monkey(listOf(74), { old -> old + 3 }, { item->item % 17 == 0.toLong() }, 0, 1)
    )
     */

    val monkeysPart1 = startingMonkeys.map { it.copy() }
    val monkeysPart2 = startingMonkeys.map { it.copy() }

    val rounds = 20
    for (round in 1..rounds) {
        monkeysPart1.forEachIndexed { index, monkey ->
            monkey.items.forEach { worryLevel ->
                monkey.itemsIspected++
                val newWorryLevel = monkey.operation(worryLevel) / 3
                val newMonkeyIndex = if (monkey.test(newWorryLevel)) {
                    monkey.monkeyIfTrue
                } else {
                    monkey.monkeyIfFalse
                }
                monkeysPart1[newMonkeyIndex].items.add(newWorryLevel)
            }
            monkey.items.clear()
        }
    }

    val roundsPart2 = 10_000
    for (round in 1..roundsPart2) {
        monkeysPart2.forEachIndexed { index, monkey ->
            monkey.items.forEach { worryLevel ->
                monkey.itemsIspected++
                val newWorryLevel = monkey.operation(worryLevel)
                // val newWorryLevelMod = newWorryLevel % (19*23*13*17).toLong()
                val newWorryLevelMod = newWorryLevel % (17*3*19*7*2*5*11*13).toLong()
                val newMonkeyIndex = if (monkey.test(newWorryLevel)) {
                    monkey.monkeyIfTrue
                } else {
                    monkey.monkeyIfFalse
                }
                monkeysPart2[newMonkeyIndex].items.add(newWorryLevelMod)
            }
            monkey.items.clear()
        }
    }

    val answer1 = monkeyBusiness(monkeysPart1)
    val answer2 = monkeyBusiness(monkeysPart2)

    println(answer1)
    println(answer2)
}