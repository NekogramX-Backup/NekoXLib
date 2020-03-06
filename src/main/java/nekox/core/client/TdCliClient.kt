package nekox.core.client

import td.TdApi

open class TdCliClient(options: TdOptions) : TdClient(options) {

    constructor() : this(TdOptions())
    constructor(path: String) : this(TdOptions().databaseDirectory(path))

    override suspend fun onAuthorizationState(authorizationState: TdApi.AuthorizationState) {

        super.onAuthorizationState(authorizationState)

    }

}