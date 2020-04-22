import kotlinx.coroutines.*

fun main(args: Array<String>) = runBlocking {

    val jobs = arrayListOf<Job>()

    jobs += launch(Dispatchers.Unconfined) {
        println(" `Unconfined` : I'm working in thread ${Thread.currentThread().name}")

        delay(100) // Alternative #1: will run on different thread after suspending function
        // yield()          // Alternative #2: will run on same thread

        println(" `Unconfined` : After delay in thread ${Thread.currentThread().name}")
    }

    jobs.forEach{ it -> it.join() }
}

