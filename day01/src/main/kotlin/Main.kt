import java.io.File

fun main(args: Array<String>) {
    val filename = args[0]
    var currentCalories = 0
    val caloriesList = mutableListOf<Int>()
    File(filename).forEachLine {
        if (it.isBlank()) {
            caloriesList.add(currentCalories)
            currentCalories = 0
        } else {
            currentCalories += it.toInt()
        }
    }
    caloriesList.add(currentCalories) // don't forget the last group :]
    val answer1 = caloriesList.max()
    val answer2 = caloriesList.sortedDescending().take(3).sum()
    println(answer1)
    println(answer2)
}
