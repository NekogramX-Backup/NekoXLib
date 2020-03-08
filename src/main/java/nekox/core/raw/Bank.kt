@file:Suppress(
    "unused"
)

package nekox.core.raw

import kotlinx.coroutines.*
import td.TdApi.*
import nekox.core.client.*

/**
 * Returns information about a bank card
 *
 * @bankCardNumber - The bank card number
 */
suspend fun TdAbsHandler.getBankCardInfo(
        bankCardNumber: String? = null
) = sync<BankCardInfo>(
        GetBankCardInfo(
                bankCardNumber
        )
)

suspend fun TdAbsHandler.getBankCardInfoOrNull(
        bankCardNumber: String? = null
) = syncOrNull<BankCardInfo>(
        GetBankCardInfo(
                bankCardNumber
        )
)

fun TdAbsHandler.getBankCardInfo(
        bankCardNumber: String? = null,
        block: (suspend CoroutineScope.(BankCardInfo) -> Unit)
) = send(
        GetBankCardInfo(
                bankCardNumber
        ), block = block
)
