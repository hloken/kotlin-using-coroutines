import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce

data class Comment(var count: Int)

fun main(args: Array<String>) = runBlocking<Unit> {
    val discussion = Channel<Comment>()

    launch(coroutineContext) { child("he did it", discussion)}
    launch(coroutineContext) { child("she did it", discussion)}

    discussion.send(Comment(0))
    delay(1000)
    coroutineContext.cancel()
}

suspend fun child(text: String, discussion: Channel<Comment>) {
    for (comment in discussion) {
        comment.count++
        log("$text $comment")
        delay(300)
        discussion.send(comment)
    }
}

fun log(msg: String) {
    println("$msg in ${Thread.currentThread().name}")
}
