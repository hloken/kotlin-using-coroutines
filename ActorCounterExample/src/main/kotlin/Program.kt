import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import kotlin.system.measureTimeMillis

suspend fun CoroutineScope.run(numberOfJobs: Int, count: Int, action: suspend ()-> Unit): Long {

    // action is repeated by each co-routine
    return measureTimeMillis {
        val jobs = List(numberOfJobs) {
            launch(coroutineContext) {
                repeat(count) { action() }
            }
        }
        jobs.forEach{ it.join() }
    }
}

sealed class CounterMsg
object InitCounter : CounterMsg()
object IncCounter: CounterMsg()
class GetCounter(val response: CompletableDeferred<Int>) : CounterMsg()

fun CoroutineScope.counterActor() = actor<CounterMsg> {
    var counter = 0
    for(msg in channel) {
        when (msg) {
            is InitCounter -> counter = 0
            is IncCounter -> counter++
            is GetCounter -> msg.response.complete(counter)
        }
    }
}

fun main(args: Array<String>) = runBlocking<Unit> {
    val jobs = 1000
    val count = 1000

    val counter = counterActor()

    counter.send(InitCounter)

    val time = run (jobs, count) {
        counter.send(IncCounter)
    }

    var response = CompletableDeferred<Int>()

    log("Completed ${jobs * count} actions in $time ms")

    counter.send(GetCounter(response))
    log("result is ${response.await()}")

    counter.close()
}

fun log(msg: String) {
    println("$msg in ${Thread.currentThread().name}")
}
