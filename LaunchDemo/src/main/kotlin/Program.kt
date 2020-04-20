import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main(args: Array<String>) {

    // Using Coroutine launch-builder
    GlobalScope.launch {
        delay(1000)
        println("World")
    }

    println("Hello, ")

    Thread.sleep(1500)
    // End Coroutine launch-builder

//    // Using Threads
//    thread {
//        Thread.sleep(1000)
//        println("World")
//    }
//
//    println("Hello, ")
//
//    Thread.sleep(1500)
//    // End Threads
}