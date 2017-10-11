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

package com.waicool20.skrypton.util

import java.net.URL
import java.net.URLClassLoader
import java.nio.file.Path

object SystemUtils {
    fun loadLibrary(path: Path, loadDirectly: Boolean = false) = loadLibrary(listOf(path), loadDirectly)

    fun loadLibrary(paths: List<Path>, loadDirectly: Boolean = false) {
        if (loadDirectly) {
            paths.forEach { System.load(it.toAbsolutePath().normalize().toString()) }
        } else {
            val separator = if (OS.isWindows()) ";" else ":"
            val libs = paths.map { it.toAbsolutePath().parent.toString() }.toMutableSet()
            libs.addAll(System.getProperty("java.library.path").split(separator).toSet())

            System.setProperty("java.library.path", libs.joinToString(separator))
            with(ClassLoader::class.java.getDeclaredField("sys_paths")) {
                isAccessible = true
                set(null, null)
            }

            paths.forEach {
                try {
                    val libName = it.fileName.toString().takeWhile { it != '.' }.let {
                        if (OS.isUnix()) it.replaceFirst("lib", "") else it
                    }
                    System.loadLibrary(libName)
                } catch (e: UnsatisfiedLinkError) {
                    System.load(it.toAbsolutePath().normalize().toString())
                }
            }
        }
    }

    // SikuliX doesn't support Java9 anyways ¯\_(ツ)_/¯
    private val classLoader by lazy { ClassLoader.getSystemClassLoader() as URLClassLoader }
    private val loaderMethod by lazy { URLClassLoader::class.java.getDeclaredMethod("addURL", URL::class.java) }

    fun loadJarLibrary(jar: Path) = loadJarLibrary(listOf(jar))

    fun loadJarLibrary(jars: List<Path>) {
        jars.map { it.toUri().toURL() }.forEach {
            if (!classLoader.urLs.contains(it)) {
                loaderMethod.isAccessible = true
                loaderMethod.invoke(classLoader, it)
            }
        }
    }

    val mainClassName: String by lazy {
        try {
            throw Exception()
        } catch (e: Exception) {
            e.stackTrace.last().className
        }
    }
}

object OS {
    fun is32Bit() = !is64Bit()
    fun is64Bit() = System.getProperty("os.arch").contains("64")
    fun isWindows() = System.getProperty("os.name").toLowerCase().contains("win")
    fun isLinux() = System.getProperty("os.name").toLowerCase().contains("linux")
    fun isMac() = System.getProperty("os.name").toLowerCase().contains("mac")

    fun isUnix() = !isWindows()
    fun isDos() = isWindows()

    val libraryExtention by lazy {
        when {
            isLinux() -> ".so"
            isWindows() -> ".dll"
            isMac() -> ".dylib"
            else -> throw Exception("Unknown OS")
        }
    }
}

operator fun Path.div(path: String): Path = resolve(path)



