
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.RecursiveTask
import kotlin.system.measureTimeMillis

val SEQUENTIAL_THRESHOLD = 5000

internal class Sum(private var array: IntArray, private var low: Int, private var high: Int) : RecursiveTask<Long>() {

    override fun compute(): Long {
        return if (high - low <= SEQUENTIAL_THRESHOLD) {
            (low until high)
                .map { array[it].toLong() }
                .sum()
        } else {
            val mid = low + (high - low) / 2
            val left = Sum(array, low, mid)
            val right = Sum(array, mid, high)
            left.fork()
            val rightAns = right.compute()
            val leftAns = left.join()
            leftAns + rightAns

        }
    }

    companion object {
        val SEQUENTIAL_THRESHOLD = 5000

        fun sumArray(array: IntArray): Long {
            return ForkJoinPool.commonPool().invoke(Sum(array, 0, array.size))
        }
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
        result = Sum.sumArray(list.toIntArray())
    }

    result = 0L
    time = measureTimeMillis {
        result = Sum.sumArray(list.toIntArray())
    }

    print("$result in ${time}ms")
}