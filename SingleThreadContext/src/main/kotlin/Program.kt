import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) = runBlocking {

    // Alternative #1: this will create a new thread for the coroutine but not ensure that it is released
//    val job = launch(newSingleThreadContext("STC")) {
//        println("        'newSingleThreadContext'     : I'm working in thread: ${Thread.currentThread().name}")
//    }
//
//    job.join()

    // Alternative #2: scoping the the newSingleThreadedContext with .use will ensure that the thread created for the coroutine gets released
    newSingleThreadContext("SingleThreadContext").use { ctx ->
        val job = launch(ctx) {
            println("        'newSingleThreadContext'     : I'm working in thread: ${Thread.currentThread().name}")
        }

        job.join()
    }


}
