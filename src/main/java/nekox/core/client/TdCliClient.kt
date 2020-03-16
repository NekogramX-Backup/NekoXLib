package nekox.core.client

import cn.hutool.core.lang.Console
import nekox.core.defaultLog
import nekox.core.raw.*
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

            println("验证码已发送. ( 使用 resend 重新发送 )")

            while (true) {

                Console.print("输入验证码: ")

                val code = Console.input()

                if (code == "resend") {

                    resendAuthenticationCode()

                    println("已重新发送.")

                } else try {

                    checkAuthenticationCode(code)

                    break

                } catch (e: TdException) {

                    defaultLog.error("验证码错误: ${e.message}")

                }

            }

        } else if (authorizationState is TdApi.AuthorizationStateWaitPassword) {

            println("您的账号设置了密码, ( 使用 destroy 直接删号 )")

            while (true) {

                Console.print("输入密码: ")

                val pswd = Console.input()

                if (pswd == "destroy") {

                    deleteAccount("delete from nekox login cli")

                    println("已删除.")

                    break

                } else try {

                    checkAuthenticationPassword(pswd)

                    break

                } catch (e: TdException) {

                    defaultLog.error("密码错误: ${e.message}")

                }

            }

        }

    }

}