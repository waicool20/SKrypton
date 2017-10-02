package com.waicool20.skrypton

import org.slf4j.LoggerFactory
import java.io.InputStream
import java.net.URI
import java.nio.file.*
import java.nio.file.attribute.PosixFilePermission

fun main(args: Array<String>) {
    SKryptonSetup.start()
}

object SKryptonSetup {
    val logger = LoggerFactory.getLogger(SKryptonSetup::class.java)
    val skryptonAppDir = Paths.get(System.getProperty("user.home")).resolve(".skrypton")
    val codeSource = javaClass.protectionDomain.codeSource.location.toURI().path

    fun start() {
        logger.debug("Starting setup")
        if (Files.notExists(skryptonAppDir)) Files.createDirectories(skryptonAppDir)
        if (codeSource.endsWith(".jar")) {
            val jarURI = URI.create("jar:file:$codeSource")
            val env = mapOf(
                    "create" to "false",
                    "encoding" to "UTF-8"
            )
            (FileSystems.newFileSystem(jarURI, env)).use { fs ->
                Files.walk(fs.getPath("/skrypton"))
                        .filter { it.nameCount > 1 }
                        .forEach(SKryptonSetup::copyResource)
            }
        } else {
            // For use in IDE
            Files.walk(Paths.get("native/build/resources/main/skrypton"))
                    .filter { it.nameCount > 1 }
                    .forEach(SKryptonSetup::copyResource)
        }
        logger.debug("Done!")
    }

    private fun copyResource(resource: Path) {
        val dest = skryptonAppDir.resolve("${resource.subpath(1, resource.nameCount)}")
        copy(resource, dest)
        if (OS.isUnix()) {
            val perms = Files.getPosixFilePermissions(dest).toMutableSet()
            perms.addAll(listOf(PosixFilePermission.OWNER_EXECUTE))
            Files.setPosixFilePermissions(dest, perms)
        }
    }

    private fun <T> copy(source: T, target: Path, overwrite: Boolean = false) {
        if (Files.notExists(target)) {
            Files.createDirectories(target.parent)
            when (source) {
                is Path -> {
                    logger.debug("[COPY] $source to $target")
                    if (overwrite) {
                        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING)
                    } else {
                        Files.copy(source, target)
                    }
                }
                is InputStream -> {
                    logger.debug("[COPY] $target")
                    if (overwrite) {
                        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING)
                    } else {
                        Files.copy(source, target)
                    }
                }
                else -> throw IllegalArgumentException()
            }
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
