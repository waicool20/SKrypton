package com.waicool20.skrypton.setup

import javafx.application.Application
import tornadofx.*
import java.nio.file.Paths

fun main(args: Array<String>) {
    Application.launch(SKryptonSetup::class.java, *args)
}

class SKryptonSetup : App(WelcomeView::class) {
    companion object {
        val skryptonAppDir = Paths.get(System.getProperty("user.home")).resolve(".skrypton")
    }
}
