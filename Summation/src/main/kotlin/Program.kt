import kotlin.system.measureTimeMillis

val SEQUENTIAL_THRESHOLD = 5000

fun compute(array: IntArray, low: Int, high:Int): Long {
//    println("low: $low, high: $high  on ${Thread.currentThread().name}")

    return if (high - low <= SEQUENTIAL_THRESHOLD) {
        (low until high)
            .map { array[it].toLong() }
            .sum()
    } else {
        val mid = low + (high - low) / 2
        val left = compute(array, low, mid)
        val right = compute(array, mid, high)
        return left + right
    }
}

fun main(args: Array<String>) {

    val list = mutableListOf<Int>()

    var limit = 20_000_000

    while (limit > 0) {
        list.add(limit--)
    }

    var result = 0L
    var time = measureTimeMillis {
        result = compute(list.toIntArray(), 0, list.toIntArray().size)
    }

    result = 0L
    time = measureTimeMillis {
        result = compute(list.toIntArray(), 0, list.toIntArray().size)
    }

    println("$result in ${time}ms")
}