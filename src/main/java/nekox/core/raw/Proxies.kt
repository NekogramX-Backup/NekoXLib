@file:Suppress(
    "unused"
)

package nekox.core.raw

import kotlinx.coroutines.*
import td.TdApi.*
import nekox.core.client.*

/**
 * Returns list of proxies that are currently set up
 * Can be called before authorization
 */
suspend fun TdAbsHandler.getProxies() = sync<Proxies>(
        GetProxies()
)

suspend fun TdAbsHandler.getProxiesOrNull() = syncOrNull<Proxies>(
        GetProxies()
)

fun TdAbsHandler.getProxies(
        block: (suspend CoroutineScope.(Proxies) -> Unit)
) = send(
        GetProxies(), block = block
)
