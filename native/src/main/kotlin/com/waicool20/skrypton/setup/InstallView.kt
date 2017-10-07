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
        Installer.createDirectories {
            Platform.runLater { progressText.text = "[CREATE] $it" }
        }

        Installer.copySKryptonLibs { file, index, total ->
            Platform.runLater {
                progressText.text = "[COPY] $file"
                progressBar.progress = index / total.toDouble()
            }
        }

        Platform.runLater { progressText.text = "[INSTALL] SikuliX libraries" }
        Installer.installSikuliXLibs()
        Platform.runLater { progressText.text = "All done!" }
        Installer.cleanup()
    }
}
