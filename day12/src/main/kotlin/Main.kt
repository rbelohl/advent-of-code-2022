import java.io.File

fun find(grid: List<List<Char>>, element: Char) : Pair<Int, Int> {
    for (i in grid.indices) {
        for (j in grid[i].indices) {
            if (grid[i][j] == element)
                return Pair(i, j)
        }
    }
    return Pair(-1, -1)
}

fun findAll(grid: List<List<Char>>, element: Char) : List<Pair<Int, Int>> {
    val result = mutableListOf<Pair<Int, Int>>()
    for (i in grid.indices) {
        for (j in grid[i].indices) {
            if (grid[i][j] == element)
                result += Pair(i, j)
        }
    }
    return result.toList()
}

fun height(c: Char) : Int {
    if (c == 'S') return 1
    if (c == 'E') return 26
    return c - 'a' + 1
}

fun isInBounds(i: Int, j: Int, width: Int, height: Int) : Boolean {
    return i >= 0 && j >= 0 && i < height && j < width
}

fun neighbors(position: Pair<Int, Int>, width: Int, height: Int) : List<Pair<Int, Int>> {
    val startI = position.first
    val startJ = position.second
    val candidateNeighbors = listOf(
        Pair(startI - 1, startJ),
        Pair(startI + 1, startJ),
        Pair(startI, startJ - 1),
        Pair(startI, startJ + 1)
    )
    return candidateNeighbors.filter { (i, j) -> isInBounds(i, j, width, height) }
}

fun bfs(heightMap: List<List<Int>>, start: Pair<Int,Int>, end: Pair<Int, Int>) : Int {
    val width = heightMap[0].size
    val height = heightMap.size
    val visited = mutableMapOf(start to 0)

    val queue = mutableListOf(start)

    while (queue.isNotEmpty()) {
        val current = queue.removeFirst()
        val distance = visited[current]!!
        if (current == end) {
            return distance
        }
        val neighbors = neighbors(current, width, height)
            .filter { !visited.contains(it) }
            .filter { heightMap[it.first][it.second] - heightMap[current.first][current.second] <= 1 }

        queue += neighbors
        neighbors.forEach {
            visited[it] = distance + 1
        }
    }

    return -1
}


fun main(args: Array<String>) {
    val filename = args[0]

    val input = File(filename).readLines()
        .map { line -> line.toList() }
    val start = find(input, 'S')
    val end = find(input, 'E')
    val heightMap = input.map { row ->
        row.map(::height)
    }
    val answer1 = bfs(heightMap, start, end)

    val startingPoints = findAll(input, 'a')
    val answer2 = startingPoints.map { bfs(heightMap, it, end) }
        .filter { it != -1 }
        .min()

    println(answer1)
    println(answer2)
}