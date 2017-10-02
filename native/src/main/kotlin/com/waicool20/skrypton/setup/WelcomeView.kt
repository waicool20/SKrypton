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

import javafx.scene.control.Button
import javafx.scene.layout.GridPane
import javafx.scene.layout.VBox
import javafx.scene.text.TextAlignment
import org.slf4j.LoggerFactory
import tornadofx.*
import java.awt.Desktop
import kotlin.concurrent.thread

class WelcomeView : View() {
    override val root by fxml<GridPane>("/welcome.fxml")
    private val descriptionBox by fxid<VBox>()
    private val continueButton by fxid<Button>()

    private val logger = LoggerFactory.getLogger(javaClass)

    init {
        logger.debug("Starting setup")
        title = "SKrypton Native components installer"
        primaryStage.isResizable = false
        with(descriptionBox) {
            text {
                textAlignment = TextAlignment.CENTER
                text = """
                The installer will install native component files required
                for the SKrypton library to run properly. This will
                overwrite any existing files.

                The files will be installed to:
                """.trimIndent()
            }
            hyperlink(SKryptonSetup.skryptonAppDir.toString()) {
                action {
                    if (Desktop.isDesktopSupported()) {
                        thread { Desktop.getDesktop().browse(SKryptonSetup.skryptonAppDir.toUri()) }
                    }
                }
            }
        }
        continueButton.setOnAction {
            replaceWith(InstallView::class, ViewTransition.Slide(0.2.seconds), true, true)
        }
    }
}
