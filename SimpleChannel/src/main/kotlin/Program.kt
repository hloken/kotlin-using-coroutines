import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlin.random.Random

fun main(args: Array<String>) = runBlocking {

    val channel = Channel<Int>()

    val job = launch {
        for (x in 1..5) {
            println("send $x")
            channel.send(x)
        }
        channel.close()
    }

    for (y in channel) {
        println("receive: $y")
    }

    job.join()
}

fun log(msg: String) {
    println("$msg in ${Thread.currentThread().name}")
}
