import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

fun main(args: Array<String>) = runBlocking {

    // Use GlobalScope.launch instead of launch to keep the inner coroutine from having the outer as parent
    val outer = launch(Dispatchers.IO) {
        try {
            launch(Dispatchers.IO) {
                try {
                    repeat(1000) {
                        print(".")
                        delay(1)
                    }
                } catch(ex: CancellationException) {
                    println("Inner exception")
                }
                throw CancellationException()
            }
        } catch(ex: CancellationException) {
            println("Outer exception")
        }
    }

    // #1: will wait for inner if it is a child of outer, otherwise inner won't finish (if using GlobalScope.launch for example)
//    outer.join()

    // #2: will cancel outer and inner if inner is a child, otherwise inner won't be cancelled and will continue until program shuts down after delay
//    outer.cancelAndJoin()
//    delay(1000)

    // #3: will cancel inner and throw a CancellationException from suspending functions called inside inner if inner is a child, CancellationException gets swallowed by launch
    delay(200)
    outer.cancelChildren()

    println()
//    println("Finished")
    println("Outer is cancelled? ${outer.isCancelled}")
}
