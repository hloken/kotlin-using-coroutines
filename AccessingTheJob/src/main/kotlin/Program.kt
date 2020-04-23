import kotlinx.coroutines.*

fun main(args: Array<String>) = runBlocking {

    val job = launch {
        println("isActive? ${coroutineContext[Job]!!.isActive}")
    }

    job.join()
}

