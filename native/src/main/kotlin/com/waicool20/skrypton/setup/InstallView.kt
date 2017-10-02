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

package com.waicool20.skrypton.setup

import javafx.application.Platform
import javafx.scene.control.ProgressBar
import javafx.scene.control.TextArea
import javafx.scene.layout.GridPane
import javafx.scene.text.Text
import org.sikuli.script.ImagePath
import org.slf4j.LoggerFactory
import tornadofx.*
import java.io.InputStream
import java.io.OutputStream
import java.io.PrintStream
import java.net.URI
import java.nio.file.*
import java.nio.file.attribute.PosixFilePermission
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread
import kotlin.streams.toList

class InstallView : View() {
    override val root by fxml<GridPane>("/install.fxml")

    private val progressText by fxid<Text>()
    private val progressTextArea by fxid<TextArea>()
    private val progressBar by fxid<ProgressBar>()

    private val logger = LoggerFactory.getLogger(javaClass)
    private val skryptonAppDir = SKryptonSetup.skryptonAppDir
    private val codeSource = javaClass.protectionDomain.codeSource.location.toURI().path

    init {
        title = "SKrypton Native components installer"
        val textOutputStream = object : OutputStream() {
            private var buffer = mutableListOf<Char>()
            override fun write(byte: Int) {
                val char = byte.toChar()
                buffer.add(char)
                if (char == '\n') flush()
            }

            override fun flush() {
                val text = buffer.joinToString("")
                buffer.clear()
                Platform.runLater {
                    progressTextArea.appendText(text)
                }
            }
        }
        System.setOut(PrintStream(TeeOutputStream(System.out, textOutputStream)))
        thread {
            TimeUnit.MILLISECONDS.sleep(300)
            startInstall()
        }
    }

    private fun startInstall() {
        if (Files.notExists(skryptonAppDir)) {
            Platform.runLater { progressText.text = "[CREATE] $skryptonAppDir" }
            Files.createDirectories(skryptonAppDir)
        }
        if (codeSource.endsWith(".jar")) {
            val jarURI = URI.create("jar:file:$codeSource")
            val env = mapOf(
                    "create" to "false",
                    "encoding" to "UTF-8"
            )
            (FileSystems.newFileSystem(jarURI, env)).use { fs ->
                copyResources(fs.getPath("/skrypton"), 1)
            }
        } else {
            // For use in IDE
            val releaseResources = Paths.get("native/build/resources/release/skrypton")
            val debugResources = Paths.get("native/build/resources/debug/skrypton")
            when {
                Files.exists(releaseResources) -> copyResources(releaseResources, 5)
                Files.exists(debugResources) -> copyResources(debugResources, 5)
                else -> error("No resource files were found")
            }
        }
        Platform.runLater { progressText.text = "[INSTALL] SikuliX libraries" }
        logger.debug("Installing SikuliX libraries")
        ImagePath.getBundleFolder()
        Platform.runLater { progressText.text = "All done!" }
        logger.debug("All done!")
    }

    private fun copyResources(rootPath: Path, dropElements: Int) {
        val jobs = Files.walk(rootPath).filter { it.nameCount > dropElements && !Files.isDirectory(it)}
                .map { it to skryptonAppDir.resolve("${it.subpath(dropElements, it.nameCount)}") }.toList()

        jobs.forEachIndexed { index, (source, dest) ->
            Platform.runLater {
                progressText.text = "[COPY] ${source.fileName}"
                progressBar.progress = index / jobs.size.toDouble()
            }
            copy(source, dest)
            if (OS.isUnix()) {
                val perms = Files.getPosixFilePermissions(dest).toMutableSet()
                perms.addAll(listOf(PosixFilePermission.OWNER_EXECUTE))
                Files.setPosixFilePermissions(dest, perms)
            }
        }
    }

    private fun <T> copy(source: T, target: Path) {
        Files.createDirectories(target.parent)
        when (source) {
            is Path -> {
                logger.debug("[COPY] $source to $target")
                Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING)
            }
            is InputStream -> {
                logger.debug("[COPY] $target")
                Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING)
            }
            else -> error("Can only copy from Path or InputStream object")
        }
    }
}
