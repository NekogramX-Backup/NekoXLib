@file:Suppress(
    "unused"
)

package nekox.core.raw

import kotlinx.coroutines.*
import td.TdApi.*
import nekox.core.client.*

/**
 * Changes the user answer to a poll
 * A poll in quiz mode can be answered only once
 *
 * @chatId - Identifier of the chat to which the poll belongs
 * @messageId - Identifier of the message containing the poll
 * @optionIds - 0-based identifiers of answer options, chosen by the user
 *              User can choose more than 1 answer option only is the poll allows multiple answers
 */
suspend fun TdAbsHandler.setPollAnswer(
        chatId: Long,
        messageId: Long,
        optionIds: IntArray
) = sync<Ok>(
        SetPollAnswer(
                chatId,
                messageId,
                optionIds
        )
)

suspend fun TdAbsHandler.setPollAnswerOrNull(
        chatId: Long,
        messageId: Long,
        optionIds: IntArray
) = syncOrNull<Ok>(
        SetPollAnswer(
                chatId,
                messageId,
                optionIds
        )
)

fun TdAbsHandler.setPollAnswer(
        chatId: Long,
        messageId: Long,
        optionIds: IntArray,
        block: (suspend CoroutineScope.(Ok) -> Unit)
) = send(
        SetPollAnswer(
                chatId,
                messageId,
                optionIds
        ), block = block
)

/**
 * Stops a poll
 * A poll in a message can be stopped when the message has can_be_edited flag set
 *
 * @chatId - Identifier of the chat to which the poll belongs
 * @messageId - Identifier of the message containing the poll
 * @replyMarkup - The new message reply markup
 *                For bots only
 */
suspend fun TdAbsHandler.stopPoll(
        chatId: Long,
        messageId: Long,
        replyMarkup: ReplyMarkup? = null
) = sync<Ok>(
        StopPoll(
                chatId,
                messageId,
                replyMarkup
        )
)

suspend fun TdAbsHandler.stopPollOrNull(
        chatId: Long,
        messageId: Long,
        replyMarkup: ReplyMarkup? = null
) = syncOrNull<Ok>(
        StopPoll(
                chatId,
                messageId,
                replyMarkup
        )
)

fun TdAbsHandler.stopPoll(
        chatId: Long,
        messageId: Long,
        replyMarkup: ReplyMarkup? = null,
        block: (suspend CoroutineScope.(Ok) -> Unit)
) = send(
        StopPoll(
                chatId,
                messageId,
                replyMarkup
        ), block = block
)
