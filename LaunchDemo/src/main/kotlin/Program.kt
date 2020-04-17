import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

fun main(args: Array<String>)  = runBlocking {

    // Using Coroutine launch-builder
    launch {
        delay(1000)
        println("World")
    }

    println("Hello, ")

    delay(1500)
    // End Coroutine launch-builder

    // Using Threads
    thread {
        Thread.sleep(1000)
        println("World")
    }

    println("Hello, ")

    Thread.sleep(1500)
    // End Threads
}