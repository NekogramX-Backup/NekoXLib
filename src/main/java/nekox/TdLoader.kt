package nekox

import nekox.core.defaultLog
import nekox.core.raw.setLogVerbosityLevel
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.util.concurrent.TimeUnit

object TdLoader {

    val version = "c407b24"

    val okHttpClient = OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.MINUTES)
            .followRedirects(true)
            .followSslRedirects(true)
            .build()

    fun tryLoad(libsDir: File = TdEnv.getFile("libs")) {

        val target = NativeTarget.current()

        val jniFile = File(libsDir, "${target.prefix}tdjni.${target.ext}")

        if (!jniFile.isFile) {

            runCatching {

                val url = if (target == NativeTarget.Linux) {

                    /*

                        val abi = RuntimeUtil.execForStr("uname -m")

                        val arch = when {

                            abi in arrayOf("x86_64", "amd64") -> "x86_64"
                            abi == "x86" || abi.matches("i[3-6]86".toRegex()) -> "x86"
                            abi in arrayOf("aarch64", "arm64") -> "aarch64"
                            abi.matches("armv[7-8].*".toRegex()) -> "armv7"
                            abi.startsWith("arm") -> "armv5"

                            else -> {

                                println("unsupported abi $abi")

                                error("unsupported abi")

                            }

                        }

                         */


                    "https://github.com/NekogramX/LibTDJni/releases/download/td@$version/libtdjni.so"


                } else if (target == NativeTarget.Win64) {

                    "https://github.com/NekogramX/LibTDJni/releases/download/td@$version/tdjni.dll"

                } else error("unknown dist")

                defaultLog.info("下载 TDLib 二进制文件: $url")

                okHttpClient.newCall(Request.Builder()
                        .url(url)
                        .build())
                        .execute().body?.byteStream()?.use {

                    jniFile.parentFile?.mkdirs()

                    jniFile.createNewFile()

                    jniFile.outputStream().use { out -> it.copyTo(out) }

                }


            }.onFailure {

                defaultLog.warn(it)

            }

        }

        load(libsDir)

    }

    fun load(libsDir: File = TdEnv.getFile("libs")) {

        runCatching {

            val target = NativeTarget.current()

            System.load("$libsDir/${target.prefix}tdjni.${target.ext}")

        }.recover {

            runCatching { System.loadLibrary("tdjni") }.onFailure {

                _ ->

                throw it

            }

        }.getOrThrow()

        setLogVerbosityLevel(1)

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

}