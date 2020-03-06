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

package nekox

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.yaml.snakeyaml.Yaml
import nekox.core.*
import nekox.core.env.*
import nekox.core.utils.*
import java.util.*

object LibsLoader {

    fun load(vararg names: String) {

        for (name in names) {

            runCatching {

                val target = NativeTarget.current()

                System.load(Env.getPath("libs/${target.prefix}$name.${target.ext}"))

            }.recover {

                runCatching { System.loadLibrary(name) }.onFailure {

                    _ ->

                    throw it

                }

            }.onFailure {

                error("Unable load $name")

            }

        }

    }

    enum class NativeTarget(val prefix: String, val ext: String) {

        Linux("lib", "so"),

        MacOS("lib", "dylib"),

        Win32("", "dll"),

        Win64("", "dll");

        companion object {

            fun current(): NativeTarget = target(System.getProperty("os.name"), System.getProperty("os.arch"))

            internal fun target(os: String, arch: String): NativeTarget {

                val osLowerCase = os.toLowerCase()

                return when {

                    "linux" in osLowerCase -> Linux

                    "mac" in osLowerCase -> MacOS

                    "windows" in osLowerCase -> if ("64" in arch.toLowerCase()) Win64 else Win32

                    else -> error("Target is not supported")

                }

            }

        }

    }

    fun loadLanguages() = runBlocking {

        val dir = Env.getFile("i18n")

        val languages = dir.listFiles { _, name -> name.endsWith(".yml") }

        if (languages == null || languages.isEmpty()) error("找不到语言文件.")

        val yaml = Yaml()

        languages.forEach {

            runCatching {

                val results = LinkedList<Deferred<*>>()

                val language = yaml.loadAs(it.inputStream(), Lang::class.java)

                language::class.java.fields.forEach { field ->

                    if (field.type == String::class.java) {

                        val resStr = field.get(language) as String

                        if (resStr.startsWith("$$")) {

                            async {

                                runCatching {

                                    field.set(language, resStr.substring(2).asMarkdownV2.asHtml)

                                }.onFailure { ex ->

                                    defaultLog.warn(ex, "语言文件 ${it.name} 中 ${field.name} 解析错误 : $resStr, 已跳过.")

                                }

                            }.also(results::addLast)

                        } else if (resStr.startsWith("$")) {

                            async {

                                runCatching {

                                    field.set(language, resStr.substring(1).asMarkdown.asHtml)

                                }.onFailure { ex ->

                                    defaultLog.warn(ex, "语言文件 ${it.name} 中 ${field.name} 解析错误 : $resStr, 已跳过.")

                                }

                            }.also(results::addLast)

                        }

                    }

                }

                results.awaitAll()

                //  defaultLog.trace(JSONObject(Gson().toJson(language)).toStringPretty())

                Lang.ALL[language.LANG_ID] = language

                Lang.BY_NAME[language.LANG_NAME] = language

            }.onFailure { ex ->

                defaultLog.warn(ex, "语言文件 ${it.name} 解析错误, 已跳过.")

            }

        }

        if (Lang.ALL.isEmpty()) {

            error("无可用的语言文件.")

        }

        Lang.DEFAULT = if (Lang.BY_NAME.containsKey(Env.DEF_LANG)) {

            defaultLog.info("将 ${Env.DEF_LANG} 设置为基本语言")

            Lang.BY_NAME.get(Env.DEF_LANG)!!

        } else {

            if (Lang.ALL.containsKey(0)) {

                Lang.ALL.get(0)!!

            } else {

                Lang.ALL.values.iterator().next()!!

            }.apply {

                defaultLog.warn("找不到设定的默认语言 : ${Env.DEF_LANG} , 已重置为 $LANG_NAME.\n\n" +
                        "所有可用语言: ${Lang.BY_NAME.keys.joinToString()}")

            }

        }

    }

}