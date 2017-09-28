/*
 * GPLv3 License
 *  Copyright (c) SikuliCef by waicool20
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.waicool20.skrypton.util

import java.net.URL
import java.net.URLClassLoader
import java.nio.file.Path

object SystemUtils {
    fun loadLibrary(path: Path) = loadLibrary(listOf(path))

    fun loadLibrary(paths: List<Path>) {
        val separator = if (OS.isWindows()) ";" else ":"
        val libs = paths.map { it.toAbsolutePath().parent.toString() }.toMutableSet()
        libs.addAll( System.getProperty("java.library.path").split(separator).toSet())

        System.setProperty("java.library.path", libs.joinToString(separator))
        with(ClassLoader::class.java.getDeclaredField("sys_paths")) {
            isAccessible = true
            set(null, null)
        }

        paths.forEach {
            val libName = it.fileName.toString().takeWhile { it != '.' }.let {
                if (OS.isUnix()) it.replaceFirst("lib", "") else it
            }
            try {
                System.loadLibrary(libName)
            } catch (e: UnsatisfiedLinkError) {
                System.load(it.toAbsolutePath().normalize().toString())
            }
        }
    }

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



