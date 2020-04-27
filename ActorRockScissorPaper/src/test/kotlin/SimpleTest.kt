import kotlinx.coroutines.test.runBlockingTest
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test

class SimpleTest {

    @Test
    fun firstTest() {
        2 `should be equal to` 1+1
    }

    @kotlinx.coroutines.ExperimentalCoroutinesApi
    @Test
    fun secondTest() = runBlockingTest {
//        doWork()
        2 `should be equal to` 1 + 1
    }
}