package nekox.core

import kotlinx.coroutines.delay
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

fun <T> mkLock() = Lock<T>()

class Lock<T> {

    val responseAtomicReference = AtomicReference<T>()

    val executedAtomicBoolean = AtomicBoolean(false)

    suspend fun waitFor(timeout: Long = 10 * 1000L): T {

        val start = System.currentTimeMillis()

        while (true) {

            if (executedAtomicBoolean.get()) {

                return responseAtomicReference.get() ?: error("null return value")

            }

            if (System.currentTimeMillis() - start > timeout) error("timeout")

            delay(100)

        }

    }

    fun send(result: T) {

        responseAtomicReference.set(result)

        executedAtomicBoolean.set(true)

    }

}