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

package nekox.core.utils

import cn.hutool.core.net.URLEncoder
import cn.hutool.core.util.CharsetUtil
import cn.hutool.http.HttpUtil
import com.google.gson.Gson
import com.pengrad.telegrambot.request.BaseRequest
import com.pengrad.telegrambot.response.BaseResponse
import kotlinx.coroutines.*
import nekox.core.client.*
import java.io.File

private val GSON = Gson()

private const val API_URL = "https://api.telegram.org"

suspend fun httpReadFile(botToken: String, filePath: String) = withContext<ByteArray>(Dispatchers.IO) {

    runCatching {

        HttpUtil.createGet(getFilePath(botToken, filePath)).execute().bodyBytes()

    }.getOrThrow()

}

private fun getFilePath(botToken: String, filePath: String): String? {

    val slash = filePath.lastIndexOf('/') + 1
    val path = filePath.substring(0, slash)
    val fileName = filePath.substring(slash)

    return API_URL + "/file/bot" + botToken + "/" + path + URLEncoder.DEFAULT.encode(fileName, CharsetUtil.CHARSET_UTF_8).replace("+", "%20")

}

fun <T : BaseRequest<T, R>, R : BaseResponse> httpSend(botToken: String, request: BaseRequest<T, R>, block: suspend CoroutineScope.(R) -> Unit): TdCallback<R> {

    val handler = TdCallback(1, block)

    GlobalScope.launch(Dispatchers.IO) {

        try {

            val result = httpSync(botToken, request)

            handler.postResult(result)

        } catch (ex: TdException) {

            handler.postError(ex)

        }

    }

    return handler

}

suspend fun <T : BaseRequest<T, R>, R : BaseResponse> httpSync(botToken: String, request: BaseRequest<T, R>): R = withContext(Dispatchers.IO) {

    val httpRequest = HttpUtil.createPost(API_URL + "/bot" + botToken + "/" + request.method)

    for ((name, value) in request.parameters) {

        if (value is ByteArray) {

            httpRequest.form(name, value, request.fileName)

        } else if (value is File) {

            httpRequest.form(name, value, request.fileName)

        } else {

            httpRequest.form(name, value)

        }

    }

    val body = try {

        httpRequest.execute().body()

    } catch (ex: Exception) {

        throw TdException(ex.message ?: ex.toString())

    }

    val response = GSON.fromJson<R>(body, request.responseType)!!

    if (!response.isOk) {

        throw TdException(response.errorCode(), response.description())

    }

    response

}