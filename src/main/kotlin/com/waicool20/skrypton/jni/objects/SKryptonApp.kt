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
import com.waicool20.skrypton.util.loggerFor
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.toList
import kotlin.system.exitProcess

object SKryptonApp : NativeInterface() {
    private val logger = loggerFor<SKryptonApp>()
    val skryptonAppDir = Paths.get(System.getProperty("user.home")).resolve(".skrypton")
    override lateinit var handle: CPointer

    // Load order
    private val nativeDependencies = run {
        val resourceFile = when {
            OS.isLinux() -> "nativeLibraries-linux.txt"
            OS.isWindows() -> "nativeLibraries-windows.txt"
            OS.isMac() -> TODO("I don't have a mac to test this stuff yet")
            else -> error("Unsupported system")
        }
        ClassLoader.getSystemClassLoader()
                .getResourceAsStream("com/waicool20/skrypton/resources/$resourceFile")
                .bufferedReader().lines().toList().filterNot { it.isNullOrEmpty() }
    }

    init {
        if (Files.notExists(skryptonAppDir)) error("Could not find SKrypton Native components folder, did you install it?")
        val libs = Files.walk(skryptonAppDir.resolve("lib"))
                .filter { Files.isRegularFile(it) && "${it.fileName}".dropWhile { it != '.' }.contains(OS.libraryExtention) }
                .sorted { path1, path2 ->
                    nativeDependencies.indexOfFirst { "${path1.fileName}".contains(it) } -
                            nativeDependencies.indexOfFirst { "${path2.fileName}".contains(it) }
                }.toList()
        libs.plusElement(skryptonAppDir.resolve(System.mapLibraryName("SKryptonNative"))).forEach {
            logger.debug { "Loading library at $it" }
            SystemUtils.loadLibrary(it, true)
        }
    }

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

    fun exec(exit: Boolean = false): Int {
        val exitCode = exec_N()
        if (exit) exitProcess(exitCode)
        return exitCode
    }
    fun runOnMainThread(action: () -> Unit) = runOnMainThread_N(Runnable { action() })

    //<editor-fold desc="Environment functions">

    fun putEnv(key: String, value: String) = putEnv_N(key, value)
    fun getEnv(key: String) = getEnv_N(key)

    //</editor-fold>

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
