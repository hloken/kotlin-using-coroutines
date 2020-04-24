import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce

fun CoroutineScope.produceNumbers(): ReceiveChannel<Int> = produce {

    var x = 1
    while(true) {
        send(x++)
    }
}

fun CoroutineScope.squareNumbers(numbers: ReceiveChannel<Int>): ReceiveChannel<Int> = produce {
    for (x in numbers) {
        send (x * x)
    }
}

fun main(args: Array<String>) = runBlocking<Unit> {

    val producer = produceNumbers()
    val square = squareNumbers(producer)

    for (i in 1..5) println(square.receive())

    println("Main done")

    square.cancel()
    producer.cancel()
}

fun log(msg: String) {
    println("$msg in ${Thread.currentThread().name}")
}
