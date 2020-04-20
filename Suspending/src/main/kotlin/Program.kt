import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) = runBlocking {

    GlobalScope.launch {
        delay(1000)
        println("World")
    }

    println("Hello, ")

    doWork()
}

suspend fun doWork() {
    // do some work
    delay(2000)
}