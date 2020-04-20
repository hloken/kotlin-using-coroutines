import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) = runBlocking<Unit> {

    launch {
        delay(1000)
        println("World")
    }

    println("Hello, ")

    delay(2000)
}