import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

fun main(args: Array<String>) = runBlocking {

    val job = launch {
        repeat(1000) {

            // Variant #1 using blocking Thread.sleep
//            print(".")
//            yield() // handles cancellation
//            Thread.sleep(1) // does not handle cancellation
//            if (!isActive) throw CancellationException()

            // Variant #2 using delay that implicitly checks for cancellation
//            print(".")
//            delay(1)

            // Variant #3 using a cancellable suspend function
            doPrint()
        }
    }

    delay(100)
    job.cancelAndJoin()
    println("done")
}

suspend fun doPrint() {
    repeat(1000) {
        if (!coroutineContext.isActive) throw CancellationException()

        print(".")
        delay(1)
    }
}
