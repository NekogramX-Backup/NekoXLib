@file:Suppress(
    "unused"
)

package nekox.core.raw

import kotlinx.coroutines.*
import td.TdApi.*
import nekox.core.client.*

/**
 * Changes the location of the current user
 * Needs to be called if GetOption("is_location_visible") is true and location changes for more than 1 kilometer
 *
 * @location - The new location of the user
 */
suspend fun TdAbsHandler.setLocation(
        location: Location? = null
) = sync<Ok>(
        SetLocation(
                location
        )
)

suspend fun TdAbsHandler.setLocationOrNull(
        location: Location? = null
) = syncOrNull<Ok>(
        SetLocation(
                location
        )
)

fun TdAbsHandler.setLocation(
        location: Location? = null,
        block: (suspend CoroutineScope.(Ok) -> Unit)
) = send(
        SetLocation(
                location
        ), block = block
)
