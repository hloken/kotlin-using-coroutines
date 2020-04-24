import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce

fun CoroutineScope.produceNumbers(): ReceiveChannel<Int> = produce {
    var x = 1 // start from 1
    while(true) {
        send(x++) // produce next
        delay(100) // wait 0.1s
    }
}

fun CoroutineScope.consumer(id: Int, channel: ReceiveChannel<Int>) = launch {
    channel.consumeEach {
        log("Processor $id received $it")
    }
}

fun main(args: Array<String>) = runBlocking<Unit> {

    val producer = produceNumbers()

    repeat(5) { consumer(it, producer) }

    println("launched")
    delay(950)
    producer.cancel()
}

fun log(msg: String) {
    println("$msg in ${Thread.currentThread().name}")
}
