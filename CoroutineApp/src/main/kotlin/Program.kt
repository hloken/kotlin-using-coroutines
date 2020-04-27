package com.rsk.introfx

import javafx.application.Platform
import javafx.beans.property.SimpleIntegerProperty
import javafx.geometry.Pos
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import kotlinx.coroutines.*
import kotlinx.coroutines.javafx.JavaFx
import tornadofx.*

fun main(args: Array<String>) = launch<CoroutineApp>(args)


class CoroutineApp : App(IntroView::class) {
    override fun start(stage: Stage) {
        stage.width = 200.0
        stage.height = 400.0
        super.start(stage)
    }
}

class IntroView : View() {
    override val root = BorderPane()
    val counter = SimpleIntegerProperty()
    lateinit var counterJob: Job

    init {
        title = "Counter"

        with(root) {
            style {
                padding = box(20.px)
            }

            center {
                vbox(10.0) {
                    alignment = Pos.CENTER

                    label {
                        bind(counter)
                        style { fontSize = 25.px }
                    }


                    button("Click to increment") {
                    }.setOnAction {
                        // Alternative #1: inside oldIncrement specifies UI-thread when updating counter
//                        GlobalScope.launch { oldIncrement() }

                        // Alternative #2: specify UI-thread when launching coroutine
                        counterJob = GlobalScope.launch(Dispatchers.JavaFx) { increment() }
                    }

                    button("Click to cancel") {
                    }.setOnAction {

                        counterJob.cancel()
                    }
                }
            }
        }
    }

    // Alternative #1, specify UI-dispatcher when updating
    fun oldIncrement() {
        Thread.sleep(3000)

        GlobalScope.launch(Dispatchers.JavaFx) {
            counter.value += 1
        }
    }

    // Alternative #2, UI-thread has been specified when launching oldIncrement-coroutine, also uses non-blocking delay instead of Thread.sleep
    suspend fun increment() {
        delay(3000)

        GlobalScope.launch(Dispatchers.JavaFx) {
            counter.value += 1
        }
    }
}
