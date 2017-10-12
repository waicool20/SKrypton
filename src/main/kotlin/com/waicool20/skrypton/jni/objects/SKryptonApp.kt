/*
 * The MIT License (MIT)
 *
 * Copyright (c) SKrypton by waicool20
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.waicool20.skrypton.jni.objects

import com.waicool20.skrypton.jni.CPointer
import com.waicool20.skrypton.jni.NativeInterface
import com.waicool20.skrypton.util.OS
import com.waicool20.skrypton.util.SystemUtils
import com.waicool20.skrypton.util.div
import com.waicool20.skrypton.util.loggerFor
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.streams.toList
import kotlin.system.exitProcess
import javafx.application.Platform

/**
 * A singleton app instance for this library and is the main entry point for any program that wants
 * to use any of the SKrypton APIs. This object is in charge of initializing the native components
 * and managing it.
 */
object SKryptonApp : NativeInterface() {
    /**
     * Holds a [Path] pointing to the directory used by SKrypton, this directory contains all the
     * native libraries and resources that SKrypton will use.
     */
    val APP_DIRECTORY: Path = Paths.get(System.getProperty("user.home")) / ".skrypton"

    private val logger = loggerFor<SKryptonApp>()

    /**
     * Native pointer, initialized in [initialize].
     */
    override lateinit var handle: CPointer

    // Get the load order from the nativeLibraries-<OS> file in resources
    private val nativeDependencies = run {
        val resourceFile = when {
            OS.isLinux() -> "nativeLibraries-linux.txt"
            OS.isWindows() -> "nativeLibraries-windows.txt"
            OS.isMac() -> TODO("I don't have a mac to test this stuff yet")
            else -> error("Unsupported system")
        }
        SKryptonApp::class.java
                .getResourceAsStream("/com/waicool20/skrypton/resources/$resourceFile")
                ?.bufferedReader()?.lines()?.toList()?.filterNot { it.isNullOrEmpty() }
                ?: error("Could not read '$resourceFile' from classpath.")
    }

    init {
        if (Files.notExists(APP_DIRECTORY)) error("Could not find SKrypton Native components folder, did you install it?")
        val libs = Files.walk(APP_DIRECTORY / "lib")
                .filter { Files.isRegularFile(it) && "${it.fileName}".dropWhile { it != '.' }.contains(OS.libraryExtention) }
                .sorted { path1, path2 ->
                    nativeDependencies.indexOfFirst { "${path1.fileName}".contains(it) } -
                            nativeDependencies.indexOfFirst { "${path2.fileName}".contains(it) }
                }.toList()
        libs.plusElement(APP_DIRECTORY / System.mapLibraryName("SKryptonNative")).forEach {
            logger.debug { "Loading library at $it" }
            SystemUtils.loadLibrary(it, true)
        }
    }

    /**
     * Initializes the native components.
     *
     * @param args Arguments to pass to the native components, recommended to just pass the array
     * received from the main function. Default is an empty array.
     * @param remoteDebugPort The remote debug port is initialized if given a valid value from 0 to 65535
     * and that the port is not previously occupied. Default is -1 (Not initialized).
     * @param action Action to be executed with the SKryptonApp instance as its receiver.
     * @return [SKryptonApp] instance (Can be used to chain [exec] function).
     */
    fun initialize(args: Array<String> = emptyArray(),
                   remoteDebugPort: Int = -1,
                   action: SKryptonApp.() -> Unit = {}
    ): SKryptonApp {
        if (remoteDebugPort in 0..65535) {
            putEnv("QTWEBENGINE_REMOTE_DEBUGGING", remoteDebugPort.toString())
        }
        handle = CPointer(initialize_N(args))
        this.action()
        return this
    }

    /**
     * Blocking function which starts the execution of the SKryptonApp.
     *
     * @param exit Whether or not to exit when SKryptonApp instance is done. Defaults to false
     * @return If [exit] is false, this function will return the exit code of the native side.
     */
    fun exec(exit: Boolean = false): Int {
        val exitCode = exec_N()
        if (exit) exitProcess(exitCode)
        return exitCode
    }

    /**
     * Executes the given action on the thread where SKryptonApp exists. This is similar to the
     * [Platform.runLater] method.
     *
     * @param action Action to be executed.
     */
    fun runOnMainThread(action: () -> Unit) = runOnMainThread_N(Runnable { action() })

    //<editor-fold desc="Environment functions">

    /**
     * Puts an variable into the native environment, it is lost when the SKryptonApp is done executing.
     *
     * @param key The name of the environment variable.
     * @param value The value of the environment variable.
     */
    fun putEnv(key: String, value: String) = putEnv_N(key, value)

    /**
     * Gets the value of the native environment variable.
     *
     * @param key The name of the environment variable.
     * @return The value of the environment variable.
     */
    fun getEnv(key: String) = getEnv_N(key)

    //</editor-fold>

    /**
     * Closes the SKryptonApp instance explicitly.
     */
    override fun close() {
        dispose_N()
    }

    //<editor-fold desc="Native functions">

    private external fun putEnv_N(key: String, value: String)
    private external fun getEnv_N(key: String): String

    private external fun runOnMainThread_N(action: Runnable)
    private external fun initialize_N(args: Array<String>): Long
    private external fun exec_N(): Int
    private external fun dispose_N()

    //</editor-fold>
}
