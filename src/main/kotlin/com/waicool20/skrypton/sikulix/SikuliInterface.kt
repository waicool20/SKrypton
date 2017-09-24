package com.waicool20.skrypton.sikulix

import org.sikuli.script.Location
import org.sikuli.script.Region
import java.awt.Rectangle

interface SikuliInterface {
    fun setLocation(loc: Location): SKryptonRegion

    fun setROI()
    fun setROI(rect: Rectangle)
    fun setROI(region: Region)
    fun setROI(X: Int, Y: Int, W: Int, H: Int)

    fun getCenter(): Location
    fun getTopLeft(): Location
    fun getTopRight(): Location
    fun getBottomLeft(): Location
    fun getBottomRight(): Location

    fun getLastMatch(): SKryptonMatch?
    fun getLastMatches(): Iterator<SKryptonMatch>

    fun offset(loc: Location?): SKryptonRegion
    fun above(): SKryptonRegion
    fun below(): SKryptonRegion
    fun left(): SKryptonRegion
    fun right(): SKryptonRegion

    fun above(height: Int): SKryptonRegion
    fun below(height: Int): SKryptonRegion
    fun left(width: Int): SKryptonRegion
    fun right(width: Int): SKryptonRegion

    /* Search operations */

    fun <PSI : Any?> find(target: PSI): SKryptonMatch?
    fun <PSI : Any?> findAll(target: PSI): Iterator<SKryptonMatch>

    fun <PSI : Any?> wait(target: PSI): SKryptonMatch
    fun <PSI : Any?> wait(target: PSI, timeout: Double): SKryptonMatch?

    fun <PSI : Any?> exists(target: PSI): SKryptonMatch?
    fun <PSI : Any?> exists(target: PSI, timeout: Double): SKryptonMatch?

    //<editor-fold desc="Mouse and KeyboardActions">
    fun click(): Int

    fun <PFRML : Any?> click(target: PFRML): Int
    fun <PFRML : Any?> click(target: PFRML, modifiers: Int): Int

    fun doubleClick(): Int
    fun <PFRML : Any?> doubleClick(target: PFRML): Int
    fun <PFRML : Any?> doubleClick(target: PFRML, modifiers: Int): Int

    fun rightClick(): Int
    fun <PFRML : Any?> rightClick(target: PFRML): Int
    fun <PFRML : Any?> rightClick(target: PFRML, modifiers: Int): Int

    fun hover(): Int
    fun <PFRML : Any?> hover(target: PFRML): Int

    fun <PFRML : Any?> dragDrop(target: PFRML): Int
    fun <PFRML : Any?> dragDrop(t1: PFRML, t2: PFRML): Int

    fun <PFRML : Any?> drag(target: PFRML): Int

    fun <PFRML : Any?> dropAt(target: PFRML): Int

    fun type(text: String): Int
    fun type(text: String, modifiers: String): Int
    fun type(text: String, modifiers: Int): Int

    fun <PFRML : Any?> type(target: PFRML, text: String): Int
    fun <PFRML : Any?> type(target: PFRML, text: String, modifiers: String): Int
    fun <PFRML : Any?> type(target: PFRML, text: String, modifiers: Int): Int


    fun paste(text: String): Int
    fun <PFRML : Any?> paste(target: PFRML, text: String): Int
    //</editor-fold>

    //<editor-fold desc="Low-level Mouse and Keyboard Actions">
    fun mouseDown(buttons: Int)

    fun mouseUp()

    fun mouseUp(buttons: Int)

    fun mouseMove(): Int
    fun mouseMove(xoff: Int, yoff: Int): Int
    fun <PFRML : Any?> mouseMove(target: PFRML): Int

    fun wheel(direction: Int, steps: Int): Int
    fun <PFRML : Any?> wheel(target: PFRML, direction: Int, steps: Int): Int
    fun <PFRML : Any?> wheel(target: PFRML, direction: Int, steps: Int, stepDelay: Int): Int

    fun keyDown(keycode: Int)
    fun keyDown(keys: String)

    fun keyUp()
    fun keyUp(keycode: Int)
    fun keyUp(keys: String)
    //</editor-fold>

    //<editor-fold desc="Highlight Action">
    fun highlight(): Region

    fun highlight(color: String): Region
    fun highlight(secs: Int): Region
    fun highlight(secs: Float): Region
    fun highlight(secs: Int, color: String): Region
    fun highlight(secs: Float, color: String): Region

    //</editor-fold>

    fun <PSIMRL> getLocationFromTarget(target: PSIMRL): Location?
}
