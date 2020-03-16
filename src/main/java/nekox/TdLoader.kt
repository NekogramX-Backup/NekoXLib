package nekox

import cn.hutool.core.util.RuntimeUtil
import cn.hutool.http.HttpUtil
import nekox.core.defaultLog
import nekox.core.raw.setLogVerbosityLevel
import java.io.File

object TdLoader {

    fun load(libsDir : File = TdEnv.getFile("libs")) {

        runCatching {

            val target = NativeTarget.current()

            System.load("$libsDir/${target.prefix}tdjni.${target.ext}")

        }.recover {

            runCatching { System.loadLibrary("tdjni") }.onFailure {

                _ ->

                throw it

            }

        }.onFailure {

            error("Unable to TDLib")

        }

        setLogVerbosityLevel(1)

    }

    fun tryLoad(libsDir : File = TdEnv.getFile("libs")) {

        val target = NativeTarget.current()

        val jniFile = File(libsDir,"${target.name}tdjni.${target.ext}")

        if (!jniFile.isFile && target == NativeTarget.Linux) {

            runCatching {

                val abi = RuntimeUtil.execForStr("uname -m")

                val arch = when {

                    abi in arrayOf("x86_64", "amd64") -> "x86_64"
                    /*
                    abi == "x86" || abi.matches("i[3-6]86".toRegex()) -> "x86"
                    abi in arrayOf("aarch64","arm64") -> "aarch64"
                    abi.matches("armv[7-8].*".toRegex()) -> "armv7"
                    abi.startsWith("arm") -> "armv5"

                     */

                    else -> {

                        println("unsupported abi $abi")

                        error("unsupported abi")

                    }

                }

                val url = "https://github.com/NekogramX/LibTDJni/releases/download/td%40c407b24/libtdjni.so"

                defaultLog.info("下载 TDLib 二进制文件")

                HttpUtil.downloadFile(url,jniFile)

            }.onFailure {

                defaultLog.warn(it)

            }

        }

        load(libsDir)

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