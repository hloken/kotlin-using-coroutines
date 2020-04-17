import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.atomic.AtomicInteger
//import kotlin.concurrent.thread

fun main(args: Array<String>) = runBlocking {

    // Using coroutines
    var result: AtomicInteger = AtomicInteger()

    for (i in 1..1_500_000) {
        launch {
            result.getAndIncrement()
        }
    }

    delay(1000)
    println(result.get())

//    // Using threads, will take a very long time
//    var result = AtomicInteger()
//
//    for (i in 1..1_500_000) {
//        thread(start = true) {
//            result.getAndIncrement()
//        }
//    }
//
//    Thread.sleep(1000)
//    println(result.get())
}