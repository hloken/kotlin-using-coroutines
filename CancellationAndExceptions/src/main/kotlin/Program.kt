import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

fun main(args: Array<String>) = runBlocking {

    val job = launch(context = NonCancellable) {

        try {
            repeat(1000) {

                yield() // handles cancellation
                print(".")
                Thread.sleep(1) // does not handle cancellation
            }
        } catch (ex: CancellationException) {
            println()
            println("Cancelled: ${ex.message}")
        } finally {
            withContext(context = NonCancellable) {
                println()
                println("In finally")
            }
        }
    }

    delay(100)
    job.cancel(CancellationException("Too many jobs"))
    job.join()
    println("done")
}

//suspend fun
