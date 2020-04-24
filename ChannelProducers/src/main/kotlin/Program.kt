import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlin.random.Random

fun CoroutineScope.produceNumbers() = produce {

    for (x in 1..5) {
        println("Send $x")
        channel.send(x)
    }
    println("Done")
}

fun main(args: Array<String>) = runBlocking {

    val channel = produceNumbers()

    channel.consumeEach {
        println("receive: $it")
    }

    println("Main done")
}

fun log(msg: String) {
    println("$msg in ${Thread.currentThread().name}")
}
