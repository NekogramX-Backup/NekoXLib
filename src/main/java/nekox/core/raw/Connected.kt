@file:Suppress(
    "unused"
)

package nekox.core.raw

import kotlinx.coroutines.*
import td.TdApi.*
import nekox.core.client.*

/**
 * Returns all website where the current user used Telegram to log in
 */
suspend fun TdAbsHandler.getConnectedWebsites() = sync<ConnectedWebsites>(
        GetConnectedWebsites()
)

suspend fun TdAbsHandler.getConnectedWebsitesOrNull() = syncOrNull<ConnectedWebsites>(
        GetConnectedWebsites()
)

fun TdAbsHandler.getConnectedWebsites(
        block: (suspend CoroutineScope.(ConnectedWebsites) -> Unit)
) = send(
        GetConnectedWebsites(), block = block
)
