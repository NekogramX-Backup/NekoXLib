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

package nekox.core.client

import td.TdApi.TdlibParameters
import nekox.core.env.*

class TdOptions {

    private var useTestDc = Env.USE_TEST_DC
    private var databaseDirectory = Env.getPath("data")
    private var filesDirectory = Env.getPath("cache/files")
    private var useFileDatabase = true
    private var useChatInfoDatabase = true
    private var useMessageDatabase = true
    private var useSecretChats = false
    private var apiId = 971882
    private var apiHash = "1232533dd027dc2ec952ba91fc8e3f27"
    private var systemLanguageCode = "en"
    private var deviceModel = "Tooko"
    private var systemVersion = "/"
    private var applicationVersion = "1.0"
    private var enableStorageOptimizer = false
    private var ignoreFileNames = false

    fun build(): TdlibParameters {

        return TdlibParameters(useTestDc, databaseDirectory, filesDirectory, useFileDatabase, useChatInfoDatabase, useMessageDatabase, useSecretChats, apiId, apiHash, systemLanguageCode, deviceModel, systemVersion, applicationVersion, enableStorageOptimizer, ignoreFileNames)

    }

    fun useTestDc(useTestDc: Boolean): TdOptions {
        this.useTestDc = useTestDc
        return this
    }

    fun databaseDirectory(databaseDirectory: String): TdOptions {
        this.databaseDirectory = databaseDirectory
        return this
    }

    fun useFileDatabase(useFileDatabase: Boolean): TdOptions {
        this.useFileDatabase = useFileDatabase
        return this
    }

    fun filesDirectory(filesDirectory: String): TdOptions {
        this.filesDirectory = filesDirectory
        return this
    }

    fun useChatInfoDatabase(useChatInfoDatabase: Boolean): TdOptions {
        this.useChatInfoDatabase = useChatInfoDatabase
        return this
    }

    fun useMessageDatabase(useMessageDatabase: Boolean): TdOptions {
        this.useMessageDatabase = useMessageDatabase
        return this
    }

    fun useSecretChats(useSecretChats: Boolean): TdOptions {
        this.useSecretChats = useSecretChats
        return this
    }

    fun apiId(apiId: Int): TdOptions {
        this.apiId = apiId
        return this
    }

    fun apiHash(apiHash: String): TdOptions {
        this.apiHash = apiHash
        return this
    }

    fun systemLanguageCode(systemLanguageCode: String): TdOptions {
        this.systemLanguageCode = systemLanguageCode
        return this
    }

    fun deviceModel(deviceModel: String): TdOptions {
        this.deviceModel = deviceModel
        return this
    }

    fun systemVersion(systemVersion: String): TdOptions {
        this.systemVersion = systemVersion
        return this
    }

    fun applicationVersion(applicationVersion: String): TdOptions {
        this.applicationVersion = applicationVersion
        return this
    }

    fun enableStorageOptimizer(enableStorageOptimizer: Boolean): TdOptions {
        this.enableStorageOptimizer = enableStorageOptimizer
        return this
    }

    fun ignoreFileNames(ignoreFileNames: Boolean): TdOptions {
        this.ignoreFileNames = ignoreFileNames
        return this
    }
}