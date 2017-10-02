package com.waicool20.skrypton

import org.sikuli.script.ImagePath
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
                        .forEach { copyResource(it) }
            }
        } else {
            // For use in IDE
            val releaseResources = Paths.get("native/build/resources/release/skrypton")
            val debugResources = Paths.get("native/build/resources/debug/skrypton")
            when {
                Files.exists(releaseResources) -> Files.walk(releaseResources)
                        .filter { it.nameCount > 5 }.forEach { copyResource(it, 5) }
                Files.exists(debugResources) -> Files.walk(debugResources)
                        .filter { it.nameCount > 5 }.forEach { copyResource(it, 5) }
                else -> error("No resource files were found")
            }
        }
        logger.debug("Installing SikuliX libraries")
        ImagePath.getBundleFolder()
        logger.debug("Done!")
    }

    private fun copyResource(resource: Path, dropElements: Int = 1) {
        val dest = skryptonAppDir.resolve("${resource.subpath(dropElements, resource.nameCount)}")
        copy(resource, dest)
        if (OS.isUnix()) {
            val perms = Files.getPosixFilePermissions(dest).toMutableSet()
            perms.addAll(listOf(PosixFilePermission.OWNER_EXECUTE))
            Files.setPosixFilePermissions(dest, perms)
        }
    }

    private fun <T> copy(source: T, target: Path, overwrite: Boolean = false) {
        if (Files.notExists(target) || overwrite) {
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
                else -> error("Can only copy from Path or InputStream object")
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
