import kotlinx.coroutines.*
import kotlin.random.Random

fun main(args: Array<String>) //{
        = runBlocking {

    // Alternative #1: Explicit call to async builders and await() inside a runBlocking
//    val job = launch {
//        val time = measureTimeMillis {
//            val r1: Deferred<Int> = async { doWorkOne() }
//            val r2: Deferred<Int> = async { doWorkTwo() }
//            println("result: ${r1.await() + r2.await()}")
//        }
//        println("Done in: $time")
//    }
//
//    job.join()

    // Alternative #2: Sequential execution of async coroutines by calling await()
//    val job = launch {
//        val result = async(coroutineContext) {
//            doWork("Work 1")
//        }
//        result.await()
//        doWork("Work 2")
//    }
//
//    job.join()

    // Alternative #3: Making the function async rather than using the async builder in caller
//    val result = doWorkAsync("Hello")
//
//    runBlocking {
//        println(result.await())
//    }

    // Alternative #4: Lazy async, coroutine does not the run until await()-call
    val job = GlobalScope.launch {
        val result = GlobalScope.async(start = CoroutineStart.LAZY) { doWorkLazy() }
        println("Result is ${result.await()}")
    }

    job.join()
}

suspend fun doWorkLazy(): Int {
    log("Be lazy")
    delay(200)
    log("Lazy done")
    return 42
}

fun doWorkAsync(msg: String): Deferred<Int> = GlobalScope.async {
    log("$msg - Working")
    delay(500)
    log("$msg - Work done")
    return@async 42
}

suspend fun doWork(msg: String): Int {
    log("$msg - Working")
    delay(500)
    log("$msg - Work done")
    return 42
}

fun log(msg: String) {
    println("$msg in ${Thread.currentThread().name}")
}

suspend fun doWorkOne(): Int {
    delay(100)
    println("Working 1")
    return Random(System.currentTimeMillis()).nextInt(42)
}

suspend fun doWorkTwo(): Int {
    delay(200)
    println("Working 2")
    return Random(System.currentTimeMillis()).nextInt(42)
}