/*
 * Copyright (c) 2019 - 2020 KazamaWataru
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress(
        "unused"
)

package nekox.core.raw

import kotlinx.coroutines.*
import td.TdApi.*
import nekox.core.client.*

/**
 * Changes the database encryption key
 * Usually the encryption key is never changed and is stored in some OS keychain
 *
 * @newEncryptionKey - New encryption key
 */
suspend fun TdAbsHandler.setDatabaseEncryptionKey(
        newEncryptionKey: ByteArray
) = sync<Ok>(
        SetDatabaseEncryptionKey(
                newEncryptionKey
        )
)

suspend fun TdAbsHandler.setDatabaseEncryptionKeyOrNull(
        newEncryptionKey: ByteArray
) = syncOrNull<Ok>(
        SetDatabaseEncryptionKey(
                newEncryptionKey
        )
)

fun TdAbsHandler.setDatabaseEncryptionKey(
        newEncryptionKey: ByteArray,
        block: (suspend CoroutineScope.(Ok) -> Unit)
) = send(
        SetDatabaseEncryptionKey(
                newEncryptionKey
        ), block = block
)

/**
 * Returns database statistics
 */
suspend fun TdAbsHandler.getDatabaseStatistics() = sync<DatabaseStatistics>(
        GetDatabaseStatistics()
)

suspend fun TdAbsHandler.getDatabaseStatisticsOrNull() = syncOrNull<DatabaseStatistics>(
        GetDatabaseStatistics()
)

fun TdAbsHandler.getDatabaseStatistics(
        block: (suspend CoroutineScope.(DatabaseStatistics) -> Unit)
) = send(
        GetDatabaseStatistics(), block = block
)
