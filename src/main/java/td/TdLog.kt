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

package td

object TdLog {

    /**
     * Changes TDLib log verbosity.
     *
     * @param verbosityLevel New value of log verbosity level. Must be non-negative.
     * Value 0 corresponds to fatal errors,
     * value 1 corresponds to java.util.logging.Level.SEVERE,
     * value 2 corresponds to java.util.logging.Level.WARNING,
     * value 3 corresponds to java.util.logging.Level.INFO,
     * value 4 corresponds to java.util.logging.Level.FINE,
     * value 5 corresponds to java.util.logging.Level.FINER,
     * value greater than 5 can be used to enable even more logging.
     * Default value of the log verbosity level is 5.
     */
    @Deprecated("As of TDLib 1.4.0 in favor of {@link TdApi.SetLogVerbosityLevel}, to be removed in the future.")
    external fun setVerbosityLevel(verbosityLevel: Int)

    /**
     * Sets file path for writing TDLib internal log. By default TDLib writes logs to the System.err.
     * Use this method to write the log to a file instead.
     *
     * @param filePath Path to a file for writing TDLib internal log. Use an empty path to
     * switch back to logging to the System.err.
     * @return whether opening the log file succeeded.
     */
    @Deprecated("As of TDLib 1.4.0 in favor of {@link TdApi.SetLogStream}, to be removed in the future.\n" + "      ")
    external fun setFilePath(filePath: String?): Boolean

    /**
     * Changes the maximum size of TDLib log file.
     *
     * @param maxFileSize The maximum size of the file to where the internal TDLib log is written
     * before the file will be auto-rotated. Must be positive. Defaults to 10 MB.
     */
    @JvmStatic
    @Deprecated("As of TDLib 1.4.0 in favor of {@link TdApi.SetLogStream}, to be removed in the future.\n" + "      ")
    external fun setMaxFileSize(maxFileSize: Long)

    /**
     * This function is called from the JNI when a fatal error happens to provide a better error message.
     * The function does not return.
     *
     * @param errorMessage Error message.
     */
    @JvmStatic
    private fun onFatalError(errorMessage: String) {

        class ThrowError constructor(private val errorMessage: String) : Runnable {
            override fun run() {
                throw RuntimeException("TDLib fatal error: $errorMessage")
            }

        }

        Thread(ThrowError(errorMessage), "TDLib fatal error thread").start()

        while (true) {
            try {
                Thread.sleep(1000) // milliseconds
            } catch (ex: InterruptedException) {
                Thread.currentThread().interrupt()
            }
        }
    }

}