import java.io.File

fun <T> List<List<T>>.flipHorizontally() : List<List<T>> {
    return this.map { it.reversed() }
}

fun <T> List<List<T>>.transpose() : List<List<T>> {
    val width = this[0].size;
    val height = this.size;
    return List(height) { i ->
        List(width) { j ->
            this[j][i]
        }
    }
}

fun calculateVisible(grid: List<List<Int>>) : List<List<Boolean>> {
    val width = grid[0].size;
    val height = grid.size;
    val visible = List(height) { MutableList(width) { false } }

    grid.forEachIndexed { i, row ->
        var maxTreeHeight = -1
        row.forEachIndexed { j, treeHeight ->
            if (treeHeight > maxTreeHeight) {
                maxTreeHeight = treeHeight
                visible[i][j] = true
            }
        }
    }
    return visible
}

fun calculateScenicScore(grid: List<List<Int>>) : List<List<Int>> {
    val width = grid[0].size;
    val height = grid.size;
    val scenicScore = List(height) { MutableList(width) { 0 } }

    grid.forEachIndexed { i, row ->
        val lastIndex = IntArray(10)
        row.forEachIndexed { j, treeHeight ->
            val score = j - lastIndex.drop(treeHeight).max()
            scenicScore[i][j] = score
            lastIndex[treeHeight] = j
        }
    }
    return scenicScore
}

fun <T, R> calculateLeftToRight(grid: List<List<T>>, calculate: (a: List<List<T>>) -> List<List<R>>) : List<List<R>> {
    return calculate(grid)
}

fun <T, R> calculateRightToLeft(grid: List<List<T>>, calculate: (a: List<List<T>>) -> List<List<R>>) : List<List<R>> {
    return calculate(grid.flipHorizontally()).flipHorizontally()
}

fun <T, R> calculateTopToBottom(grid: List<List<T>>, calculate: (a: List<List<T>>) -> List<List<R>>) : List<List<R>> {
    return calculate(grid.transpose()).transpose()
}

fun <T, R> calculateBottomToTop(grid: List<List<T>>, calculate: (a: List<List<T>>) -> List<List<R>>) : List<List<R>> {
    return calculate(grid.transpose().flipHorizontally()).flipHorizontally().transpose()
}

fun <T, R> calculateAllDirections(
    grid: List<List<T>>,
    calculate: (a: List<List<T>>) -> List<List<R>>,
    combine: (a: R, b: R) -> R)
: List<List<R>> {
    var result = calculateLeftToRight(grid, calculate)
    result = combine2dLists(result, calculateRightToLeft(grid, calculate), combine)
    result = combine2dLists(result, calculateTopToBottom(grid, calculate), combine)
    result = combine2dLists(result, calculateBottomToTop(grid, calculate), combine)
    return result
}

fun <T> combineLists(first: List<T>, second: List<T>, operation: (a : T, b: T) -> T) : List<T> {
    return first.zip(second).map { operation(it.first, it.second) }
}

fun <T> combine2dLists(first: List<List<T>>, second: List<List<T>>, operation: (a : T, b: T) -> T) : List<List<T>> {
    return first.zip(second).map { combineLists(it.first, it.second, operation) }
}

fun main(args: Array<String>) {
    val filename = args[0]
    val input = File(filename).readLines().map { it.map(Char::digitToInt) }

    val answer1 = calculateAllDirections(input, ::calculateVisible) { a, b -> a || b }
        .flatMap { it.toList() }
        .count { it }
    val answer2 = calculateAllDirections(input, ::calculateScenicScore) { a, b -> a * b }
        .flatMap { it.toList() }
        .max()
    println(answer1)
    println(answer2)
}
