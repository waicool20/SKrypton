package com.waicool20.skrypton.sikulix.input

import org.sikuli.basics.Settings
import org.sikuli.script.Key
import org.sikuli.script.KeyModifier
import org.sikuli.script.Location

class SKryptonKeyboard(val robot: SKryptonRobot) {
    companion object {
        fun parseModifiers(modifiers: String): Int {
            var mods = 0
            modifiers.toCharArray().forEach {
                mods = mods.or(when (it) {
                    Key.C_CTRL -> KeyModifier.CTRL
                    Key.C_ALT -> KeyModifier.ALT
                    Key.C_SHIFT -> KeyModifier.SHIFT
                    Key.C_META -> KeyModifier.META
                    Key.C_ALTGR -> KeyModifier.ALTGR
                    Key.C_WIN -> KeyModifier.WIN
                    else -> 0
                })
            }
            return mods
        }
    }

    @Synchronized
    fun type(location: Location?, text: String, modifiers: Int) = synchronized(this) {
        if (location != null) robot.screen.click(location)
        val pause = if (Settings.TypeDelay > 1) 1 else (Settings.TypeDelay * 1000).toInt()
        robot.pressModifiers(modifiers)
        text.toCharArray().map(Char::toInt).forEach {
            robot.typeKey(it)
            robot.delay(if (pause < 80) 80 else pause)
        }
        Settings.TypeDelay = 0.0
    }

    @Synchronized
    fun keyUp() = synchronized(this) { robot.keyUp() }

    @Synchronized
    fun keyUp(keycode: Int) = synchronized(this) { robot.keyUp(keycode) }

    @Synchronized
    fun keyUp(keys: String) = synchronized(this) { robot.keyUp(keys) }

    @Synchronized
    fun keyDown(keycode: Int) = synchronized(this) { robot.keyDown(keycode) }

    @Synchronized
    fun keyDown(keys: String) = synchronized(this) { robot.keyDown(keys) }

    @Synchronized inline fun <T> atomicAction(action: () -> T): T = synchronized(this) { action() }
}