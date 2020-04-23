import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) = runBlocking {

    println("        'runBlocking': I'm working in thread: ${Thread.currentThread().name}" )

    val job = launch(CoroutineName("kevin-context") + coroutineContext) {
        println("        'launch'     : I'm working in thread: ${Thread.currentThread().name}")
    }

    job.join()
}
