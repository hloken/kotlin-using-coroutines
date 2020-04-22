import kotlinx.coroutines.*

fun main(args: Array<String>) = runBlocking {

    val job = withTimeoutOrNull(100) {

        repeat(1000) {
            yield() // handles cancellation
            print(".")
            Thread.sleep(1) // does not handle cancellation
        }
    }

    if (job == null) {
        println("timed out")
    }
}
