import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import java.util.*

val numberOfWorkers = 10
var totalWork = 20

data class Work(var x: Long=0, var y: Long = 0, var z: Long = 0)

suspend fun worker(input: Channel<Work>, output: Channel<Work>) {
    for(w in input) {
        w.z = w.x * w.y
        delay(w.z)
        output.send(w)
    }
}

fun run() {
    val input = Channel<Work>()
    val output =  Channel<Work>()

    repeat(numberOfWorkers) {
        GlobalScope.launch { worker(input, output) }
    }

    GlobalScope.launch { sendLotsOfWork(input) }
    GlobalScope.launch { receiveLotsOfResults(output) }
}

suspend fun sendLotsOfWork(input: Channel<Work>) {
    repeat(totalWork) {
        input.send(Work((0L..100).random(), (0L..100).random()))
    }
}

suspend fun receiveLotsOfResults(channel: Channel<Work>) {
    for(work in channel)
        log("${work.x}*${work.y} = ${work.z}")
}

fun main(args: Array<String>) = runBlocking<Unit> {
    run()
    runBlocking { delay(5000) }
}

private object RandomRangesSingleton : Random()

fun ClosedRange<Long>.random() = (RandomRangesSingleton.nextInt((endInclusive.toInt() + 1) - start.toInt()) + start)

fun log(msg: String) {
    println("$msg in ${Thread.currentThread().name}")
}
