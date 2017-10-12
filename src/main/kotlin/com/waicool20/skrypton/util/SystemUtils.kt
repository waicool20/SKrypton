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

/**
 * System utility functions
 */
object SystemUtils {

    /**
     * Load a library from a given path.
     *
     * @param path Path to the library
     * @param loadDirectly If true, it will attempt to load the library directly using the absolute
     * path, otherwise it will attempt to modify the `java.library.path` variable and load it by name.
     */
    fun loadLibrary(path: Path, loadDirectly: Boolean = false) = loadLibrary(listOf(path), loadDirectly)

    /**
     * Loads libraries from a given path.
     *
     * @param paths List containing path to the libraries
     * @param loadDirectly If true, it will attempt to load the libraries directly using the absolute
     * path, otherwise it will attempt to modify the `java.library.path` variable and load it by name.
     */
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

    /**
     * Load a Jar library into classpath.
     *
     * @param jar Path to Jar
     */
    fun loadJarLibrary(jar: Path) = loadJarLibrary(listOf(jar))

    /**
     * Loads Jar libraries into classpath.
     *
     * @param jars List containing path to the jars
     */
    fun loadJarLibrary(jars: List<Path>) {
        jars.map { it.toUri().toURL() }.forEach {
            if (!classLoader.urLs.contains(it)) {
                loaderMethod.isAccessible = true
                loaderMethod.invoke(classLoader, it)
            }
        }
    }

    /**
     * Gets the name of the class which contains the main entry point function.
     */
    val mainClassName: String by lazy {
        try {
            throw Exception()
        } catch (e: Exception) {
            e.stackTrace.last().className
        }
    }
}

/**
 * Operating system related utility functions and checks.
 */
object OS {
    /**
     * 32 bit architecture check.
     *
     * @return True if system architecture is 32 bit.
     */
    fun is32Bit() = !is64Bit()

    /**
     * 64 bit architecture check.
     *
     * @return True if system architecture is 64 bit.
     */
    fun is64Bit() = System.getProperty("os.arch").contains("64")

    /**
     * Windows system check.
     *
     * @return True if system is running a Windows OS.
     */
    fun isWindows() = System.getProperty("os.name").toLowerCase().contains("win")

    /**
     * Linux system check.
     *
     * @return True if system is running a Linux distro.
     */
    fun isLinux() = System.getProperty("os.name").toLowerCase().contains("linux")

    /**
     * Mac system check.
     *
     * @return True if system is running a version of MacOs.
     */
    fun isMac() = System.getProperty("os.name").toLowerCase().contains("mac")

    /**
     * Unix system check.
     *
     * @return True if system is running a UNIX like OS.
     */
    fun isUnix() = !isWindows()

    /**
     * DOS system check.
     *
     * @return True if system is running DOS like system.
     */
    fun isDos() = isWindows()

    /**
     * System library extension format in the form of `.<extension>`.
     *
     * @return Library extension.
     * @throws IllegalStateException If the OS of the current system cannot be determined.
     */
    val libraryExtention by lazy {
        when {
            isLinux() -> ".so"
            isWindows() -> ".dll"
            isMac() -> ".dylib"
            else -> error("Unknown OS")
        }
    }
}

/**
 * Resolves give path with this path. Allows for usage like `Paths.get("foo") / "bar"`,
 * Which is identical to `Paths.get("foo").resolve("bar")`.
 *
 * @param path Path to resolve.
 * @return Resolved path.
 */
operator fun Path.div(path: String): Path = resolve(path)



