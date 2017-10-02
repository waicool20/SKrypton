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
                for the SKrypton library to run properly.

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
