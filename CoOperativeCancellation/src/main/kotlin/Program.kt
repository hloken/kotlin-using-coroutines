import kotlinx.coroutines.*

fun main(args: Array<String>) = runBlocking {

    val job = launch {
        repeat(1000) {
//            print(".")
//            yield()
//            Thread.sleep(1)
            if (coroutineContext.isActive) {
                print(".")
                Thread.sleep(10)
            }
        }
    }

    delay(100)
    job.cancelAndJoin()
    println("done")
}
