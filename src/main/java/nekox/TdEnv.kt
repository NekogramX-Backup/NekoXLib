package nekox

import java.io.File
import java.io.IOException
import java.util.concurrent.atomic.AtomicBoolean

object TdEnv {

    var ROOT_PATH: File = File(".")
    var FUN_PREFIX = arrayOf("/","!")
    var DEVELOPER = 896711046

    fun getPath(path: String) : String {
        val target = File(ROOT_PATH, path)
        return try {
            target.getCanonicalPath()
        } catch (e: IOException) {
            target.getPath()
        }
    }

    fun getFile(path: String): File {
        val target = File(ROOT_PATH, path)
        return try {
            target.getCanonicalFile()
        } catch (e: IOException) {
            target
        }
    }

    fun isAdmin(accountId: Int): Boolean {
        return accountId == DEVELOPER// || ArrayUtil.contains(ADMINS, accountId)
    }


}