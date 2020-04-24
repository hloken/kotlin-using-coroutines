import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce

suspend fun sendString(channel: Channel<String>, s: String, interval: Long) {
    while(true) {
        delay(interval)
        channel.send(s)
    }
}

fun main(args: Array<String>) = runBlocking<Unit> {
    val channel = Channel<String>()

    launch { sendString(channel, "foo", 220L)}
    launch { sendString(channel, "bar", 500L)}

    repeat(6) {
        println(channel.receive())
    }

    coroutineContext.cancelChildren()
}

fun log(msg: String) {
    println("$msg in ${Thread.currentThread().name}")
}
