import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce

fun main(args: Array<String>) = runBlocking<Unit> {
    val channel = Channel<Int>(4) // create buffered channel
    val sender = launch(Dispatchers.Default) {
        // launch sender coroutine
        repeat(10) {
            log("Sending $it") // print before sending each element
            channel.send(it) // will suspend when buffer is full
        }
    }

    // don't receive anything... just wait...
    delay(1000)
    launch(Dispatchers.Default) {
        repeat(10) {
            log(" --Receiving ${channel.receive()}")
        }
    }

    sender.cancel() // cancel sender coroutine
}

fun log(msg: String) {
    println("$msg in ${Thread.currentThread().name}")
}
