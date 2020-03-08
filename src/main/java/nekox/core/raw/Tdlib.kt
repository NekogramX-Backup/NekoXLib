@file:Suppress(
    "unused"
)

package nekox.core.raw

import kotlinx.coroutines.*
import td.TdApi.*
import nekox.core.client.*

/**
 * Sets the parameters for TDLib initialization
 * Works only when the current authorization state is authorizationStateWaitTdlibParameters
 *
 * @parameters - Parameters
 */
suspend fun TdAbsHandler.setTdlibParameters(
        parameters: TdlibParameters? = null
) = sync<Ok>(
        SetTdlibParameters(
                parameters
        )
)

suspend fun TdAbsHandler.setTdlibParametersOrNull(
        parameters: TdlibParameters? = null
) = syncOrNull<Ok>(
        SetTdlibParameters(
                parameters
        )
)

fun TdAbsHandler.setTdlibParameters(
        parameters: TdlibParameters? = null,
        block: (suspend CoroutineScope.(Ok) -> Unit)
) = send(
        SetTdlibParameters(
                parameters
        ), block = block
)
