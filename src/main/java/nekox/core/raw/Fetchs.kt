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

@file:Suppress("unused")

package nekox.core.raw

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import nekox.core.client.TdAbsHandler


suspend fun TdAbsHandler.fetchMessages(charId: Number, vararg messageIds: Long) = coroutineScope {

    messageIds.forEach { getMessageOrNull(charId.toLong(), it) }

}

fun TdAbsHandler.fetchMessages(charId: Number, vararg messageIds: Long, block: suspend CoroutineScope.() -> Unit) {

    GlobalScope.launch {

        messageIds.forEach { getMessageOrNull(charId.toLong(), it) }

        block.invoke(this)

    }

}