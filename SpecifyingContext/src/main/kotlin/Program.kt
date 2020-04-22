import kotlinx.coroutines.*

fun main(args: Array<String>) = runBlocking {

    val jobs = arrayListOf<Job>()

    createJobs(jobs)

    jobs.forEach{ it -> it.join() }

    println()
    println()
    println()
    var job = launch(Dispatchers.Default) {
        println("   'launched thread': In thread ${Thread.currentThread().name}")
        val jobs = arrayListOf<Job>()
        createJobs(jobs)
        jobs.forEach{ it -> it. join() }
    }
}

private fun CoroutineScope.createJobs(jobs: ArrayList<Job>) {
    jobs += launch {
        // the 'default' context
        println("       `default`    : In thread ${Thread.currentThread().name}")
    }
    jobs += launch(Dispatchers.Default) {
        // the 'default' context
        println("`defaultDispatcher` : In thread ${Thread.currentThread().name}")
    }
    jobs += launch(Dispatchers.Unconfined) {
        // not confined --- will work with main thread
        println("       `UnConfined` : In thread ${Thread.currentThread().name}")
    }
//    jobs += launch(Dispatchers.Main) {
//        // will work with main thread
//        println("             `Main` : In thread ${Thread.currentThread().name}")
//    }
    jobs += launch(Dispatchers.IO) {
        // will work with main thread
        println("               `IO` : In thread ${Thread.currentThread().name}")
    }
    jobs += launch(coroutineContext) {
        // context of the parent, runBlocking coroutine
        println(" `coroutinecontext` : In thread ${Thread.currentThread().name}")
    }
    jobs += launch(newSingleThreadContext("OwnThread")) {
        // will get it's own new thread
        println("           `newSTC` : In thread ${Thread.currentThread().name}")
    }
}