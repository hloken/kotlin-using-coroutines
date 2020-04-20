import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) = runBlocking {

    val job = launch {
        repeat(1000) {
            delay(100)
            print(".")
        }
    }

    delay(2500)

//    job.cancel()
//    job.join()
    job.cancelAndJoin()

    println("done")
}
