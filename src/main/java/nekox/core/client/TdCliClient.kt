package nekox.core.client

import cn.hutool.core.lang.Console
import nekox.core.defaultLog
import nekox.core.raw.checkAuthenticationCode
import nekox.core.raw.checkAuthenticationPassword
import nekox.core.raw.setAuthenticationPhoneNumber
import td.TdApi

open class TdCliClient(options: TdOptions) : TdClient(options) {

    constructor() : this(TdOptions())
    constructor(path: String) : this(TdOptions().databaseDirectory(path))

    override suspend fun onAuthorizationState(authorizationState: TdApi.AuthorizationState) {

        super.onAuthorizationState(authorizationState)

        if (authorizationState is TdApi.AuthorizationStateWaitPhoneNumber) {

            while (true) {

                Console.print("输入手机号: ")

                val phone = Console.input()

                try {

                    setAuthenticationPhoneNumber(phone)

                    break

                } catch (e: TdException) {

                    defaultLog.error("手机号无效: ${e.message}")

                }

            }

        } else if (authorizationState is TdApi.AuthorizationStateWaitCode) {

            while (true) {

                Console.print("输入验证码: ")

                val code = Console.input()

                try {

                    checkAuthenticationCode(code)

                    break

                } catch (e: TdException) {

                    defaultLog.error("验证码错误: ${e.message}")

                }

            }

        } else if (authorizationState is TdApi.AuthorizationStateWaitPassword) {

            while (true) {

                Console.print("输入密码: ")

                val pswd = Console.input()

                try {

                    checkAuthenticationPassword(pswd)

                    break

                } catch (e: TdException) {

                    defaultLog.error("密码错误: ${e.message}")

                }

            }

        }

    }

}