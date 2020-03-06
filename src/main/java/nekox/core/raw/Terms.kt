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
 * Accepts Telegram terms of services
 *
 * @termsOfServiceId - Terms of service identifier
 */
suspend fun TdAbsHandler.acceptTermsOfService(
        termsOfServiceId: String? = null
) = sync<Ok>(
        AcceptTermsOfService(
                termsOfServiceId
        )
)

suspend fun TdAbsHandler.acceptTermsOfServiceOrNull(
        termsOfServiceId: String? = null
) = syncOrNull<Ok>(
        AcceptTermsOfService(
                termsOfServiceId
        )
)

fun TdAbsHandler.acceptTermsOfService(
        termsOfServiceId: String? = null,
        block: (suspend CoroutineScope.(Ok) -> Unit)
) = send(
        AcceptTermsOfService(
                termsOfServiceId
        ), block = block
)
