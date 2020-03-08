@file:Suppress(
    "unused"
)

package nekox.core.raw

import kotlinx.coroutines.*
import td.TdApi.*
import nekox.core.client.*

/**
 * Returns saved animations
 */
suspend fun TdAbsHandler.getSavedAnimations() = sync<Animations>(
        GetSavedAnimations()
)

suspend fun TdAbsHandler.getSavedAnimationsOrNull() = syncOrNull<Animations>(
        GetSavedAnimations()
)

fun TdAbsHandler.getSavedAnimations(
        block: (suspend CoroutineScope.(Animations) -> Unit)
) = send(
        GetSavedAnimations(), block = block
)

/**
 * Manually adds a new animation to the list of saved animations
 * The new animation is added to the beginning of the list
 * If the animation was already in the list, it is removed first
 * Only non-secret video animations with MIME type "video/mp4" can be added to the list
 *
 * @animation - The animation file to be added
 *              Only animations known to the server (i.e
 *              Successfully sent via a message) can be added to the list
 */
suspend fun TdAbsHandler.addSavedAnimation(
        animation: InputFile? = null
) = sync<Ok>(
        AddSavedAnimation(
                animation
        )
)

suspend fun TdAbsHandler.addSavedAnimationOrNull(
        animation: InputFile? = null
) = syncOrNull<Ok>(
        AddSavedAnimation(
                animation
        )
)

fun TdAbsHandler.addSavedAnimation(
        animation: InputFile? = null,
        block: (suspend CoroutineScope.(Ok) -> Unit)
) = send(
        AddSavedAnimation(
                animation
        ), block = block
)

/**
 * Removes an animation from the list of saved animations
 *
 * @animation - Animation file to be removed
 */
suspend fun TdAbsHandler.removeSavedAnimation(
        animation: InputFile? = null
) = sync<Ok>(
        RemoveSavedAnimation(
                animation
        )
)

suspend fun TdAbsHandler.removeSavedAnimationOrNull(
        animation: InputFile? = null
) = syncOrNull<Ok>(
        RemoveSavedAnimation(
                animation
        )
)

fun TdAbsHandler.removeSavedAnimation(
        animation: InputFile? = null,
        block: (suspend CoroutineScope.(Ok) -> Unit)
) = send(
        RemoveSavedAnimation(
                animation
        ), block = block
)
