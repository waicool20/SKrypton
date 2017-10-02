package com.waicool20.skrypton.setup

import java.io.OutputStream

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

class TeeOutputStream(val main: OutputStream, val branch: OutputStream) : OutputStream() {
    override fun write(int: Int) {
        main.write(int)
        branch.write(int)
    }

    override fun flush() {
        super.flush()
        main.flush()
        branch.flush()
    }

    override fun close() {
        super.close()
        main.close()
        branch.close()
    }
}
