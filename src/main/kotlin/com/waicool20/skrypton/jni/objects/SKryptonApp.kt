package com.waicool20.skrypton.jni.objects

import com.waicool20.skrypton.jni.CPointer
import com.waicool20.skrypton.jni.NativeInterface
import com.waicool20.skrypton.util.OS
import com.waicool20.skrypton.util.SystemUtils
import com.waicool20.skrypton.util.loggerFor
import java.net.URLClassLoader
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.streams.toList

object SKryptonApp : NativeInterface() {
    private val logger = loggerFor<SKryptonApp>()
    private val codeSource = Paths.get(javaClass.protectionDomain.codeSource.location.toURI().path)
    val skryptonAppDir = Paths.get(System.getProperty("user.home")).resolve(".skrypton")
    override lateinit var handle: CPointer

    // Load order
    private val nativeDependencies = ClassLoader.getSystemClassLoader().getResourceAsStream("nativeLibraries.txt").bufferedReader().lines().toList()

    init {
        if (Files.notExists(skryptonAppDir)) error("Could not find SKrypton Native components folder, did you install it?")
        if (System.getenv("skryptonJvm").isNullOrEmpty()) {
            val sJvm = skryptonAppDir.resolve("bin/java")
            logger.debug("Not running under skrypton JVM")
            logger.debug("Main class: ${SystemUtils.mainClassName}")
            logger.debug("SKrypton JVM path: $sJvm")
            val args = mutableListOf(sJvm.toString())
            args += "-cp"
            args += (ClassLoader.getSystemClassLoader() as URLClassLoader).urLs
                    .joinToString(":") { it.toString().replace("file:", "") }
            args += SystemUtils.mainClassName
            logger.debug("Relaunching with command: ${args.joinToString(" ")}")
            with(ProcessBuilder(args)) {
                println(codeSource.parent.toFile())
                directory(codeSource.parent.toFile())
                inheritIO()
                environment().put("skryptonJvm", sJvm.toString())
                logger.debug("Launching new instance with skrypton JVM")
                start().waitFor()
                System.exit(0)
            }
            logger.debug("Running under skrypton JVM")
        }
        val toLoad = mutableListOf(
                Paths.get(System.getProperty("java.home")).resolve("lib/amd64/libjawt.so"),
                skryptonAppDir.resolve("bin/libSKryptonNative.so")
        )
        val libs = Files.walk(skryptonAppDir.resolve("bin/lib"))
                .filter { Files.isRegularFile(it) }
                .sorted { path1, path2 ->
                    nativeDependencies.indexOfFirst { "$path1".contains("$it${OS.libraryExtention}") } -
                            nativeDependencies.indexOfFirst { "$path2".contains("$it${OS.libraryExtention}") }
                }.toList()
        libs.plus(toLoad).forEach {
            logger.debug { "Loading library at $it" }
            SystemUtils.loadLibrary(it)
        }
    }

    fun initalize(args: Array<String> = emptyArray(), remoteDebugPort: Int = -1): SKryptonApp {
        if (remoteDebugPort in 0..65535) {
            putEnv("QTWEBENGINE_REMOTE_DEBUGGING", remoteDebugPort.toString())
        }
        handle = CPointer(initialize_N(args))
        return this
    }

    fun exec() = exec_N()
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
