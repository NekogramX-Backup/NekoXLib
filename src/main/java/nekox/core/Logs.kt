package nekox.core

import cn.hutool.core.date.DateUtil
import cn.hutool.core.lang.Console
import cn.hutool.core.lang.Dict
import cn.hutool.core.util.StrUtil
import cn.hutool.log.GlobalLogFactory
import cn.hutool.log.Log
import cn.hutool.log.LogFactory
import cn.hutool.log.dialect.console.ConsoleLog
import cn.hutool.log.level.Level
import java.io.ByteArrayOutputStream

object TookoLogFactory : LogFactory("NekoXLib Log") {

    override fun createLog(name: String?): Log {

        return if (name != null) mkLog(name) else defaultLog

    }

    override fun createLog(clazz: Class<*>?): Log {

        return if (clazz != null) mkLog(clazz.simpleName).apply {

            when (clazz) {

                GlobalLogFactory::class.java -> logLevel = Level.INFO

            }

        } else defaultLog

    }
}

val defaultLog = mkLog("NekoX")

fun mkLog(name: String) = TookoLog(name)

class TookoLog(name: String) : ConsoleLog(name) {

    var logLevel = Level.ALL

    override fun log(fqcn: String, level: Level, t: Throwable?, format: String, vararg arguments: Any) {

        if (logLevel > level) return

        var logMsg = if (t != null) {

            var logWithExc = if (t.message != format) format.input(arguments) + "\n" else ""

            val out = ByteArrayOutputStream()

            logWithExc += Fn.parseError(t)

            logWithExc

        } else {

            format.input(arguments)

        }

        val dict = Dict.create().set("date", DateUtil.now()).set("level", level.toString()).set("name", name).set("msg", logMsg)

        val logFormat = if (name.isBlank()) "[{level}] {msg}" else "[{level}] {name}: {msg}"

        logMsg = StrUtil.format(logFormat, dict)

        if (level.ordinal >= Level.WARN.ordinal) {

            Console.error(logMsg)

        } else {

            Console.log(logMsg)

        }

    }

}