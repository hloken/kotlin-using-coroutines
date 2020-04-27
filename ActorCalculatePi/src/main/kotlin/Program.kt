import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import java.util.*
import kotlin.system.measureTimeMillis

sealed class PiMessage
class Start(val response: CompletableDeferred<Double>, val workers: Long) : PiMessage()
class Work(val channel: Channel<PiMessage>, val start: Long, val end: Long, val worker: Long) : PiMessage()
class Result(val result: Double) : PiMessage()

fun piActor() = GlobalScope.actor<Work> {
    var total = 0.0

    for (msg in channel) {
        for (i in msg.start until msg.end) {
            total += 4.0 * (1 - (i % 2) * 2) / (2 * i +1)
            if (i % 50_000_000 == 0L) log("${msg.worker}")
        }
        msg.channel.send(Result(total))
    }
}

fun workerActor() = GlobalScope.actor<PiMessage> {
    lateinit var response: CompletableDeferred<Double>

    var total = 0.0
    var workers: Long = 0
    var finished: Long = 0

    val iterations: Long = 2_000_000_000

    for (msg in channel) {
        when(msg) {
            is Start -> {
                response = msg.response
                workers = msg.workers
                // break down the work into chunks
                val range: Long = iterations / workers
                for (i in (0 until workers)) {
                    val start: Long = i * range
                    val end: Long = ((i + 1) * range) - 1
                    log("Range $start to $end")
                    piActor().send(Work(channel, start, end, i))
                }
            }
            is Result -> {
                finished++
                total += msg.result
                if (finished == workers) {
                    response.complete(total)
                }
            }
        }
    }
}

// pi = 4 * (1 - 1/3 + 1/5 - 1/7 + 1/9 - ....)
fun main(args: Array<String>) = runBlocking<Unit> {
    val response = CompletableDeferred<Double>()

    var time = measureTimeMillis {
        val workerActor = workerActor()

        workerActor.send(Start(response, 1))
        val result = response.await()
        workerActor.close()

        println()
        print(result)
    }
    log(" in ${time}ms")
}

fun log(msg: String) { println("$msg in ${Thread.currentThread().name}") }

fun ClosedRange<Int>.random() = Random().nextInt(endInclusive - start) + start