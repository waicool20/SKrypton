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

package com.waicool20.skrypton.sikulix

import com.waicool20.skrypton.jni.objects.WebViewHighlighter
import com.waicool20.skrypton.sikulix.input.SKryptonKeyboard
import org.sikuli.script.*
import java.awt.Color
import java.awt.Rectangle
import java.awt.event.KeyEvent

/**
 * A class subclassing [Region], it is identical for the most part but allows the use of independent
 * mouse and keyboard actions.
 *
 * @constructor Main constructor
 * @param xPos x coordinate of the top left corner of the region.
 * @param yPos y coordinate of the top left corner of the region.
 * @param width Width of the region.
 * @param height Height of the region.
 */
open class SKryptonRegion(xPos: Int, yPos: Int, width: Int, height: Int) : Region(), ISikuliRegion {
    /**
     * Region constructor
     *
     * @param region Region to inherit from.
     * @param screen Screen that this region should belong to.
     */
    constructor(region: Region, screen: SKryptonScreen) : this(region.x, region.y, region.w, region.h, screen)

    /**
     * Rectangle constructor
     *
     * @param rect Uses the rectangles dimensions to construct this region.
     * @param screen Screen that this region should belong to.
     */
    constructor(rect: Rectangle, screen: SKryptonScreen) : this(rect.x, rect.y, rect.width, rect.height, screen)

    /**
     * Like main constructor but has additional screen param.
     *
     * @param xPos x coordinate of the top left corner of the region.
     * @param yPos y coordinate of the top left corner of the region.
     * @param width Width of the region.
     * @param height Height of the region.
     * @param screen Screen that this region should belong to.
     */
    constructor(xPos: Int, yPos: Int, width: Int, height: Int, screen: SKryptonScreen) : this(xPos, yPos, width, height) {
        this.screen = screen
    }

    init {
        x = xPos
        y = yPos
        w = width
        h = height
    }

    private val mouse by lazy { skryptonScreen().mouse }
    private val keyboard by lazy { skryptonScreen().keyboard }

    /**
     * Gets the screen this region belongs to as a [SKryptonScreen]
     */
    fun skryptonScreen() = screen as SKryptonScreen

    override fun setLocation(loc: Location): SKryptonRegion {
        x = loc.x
        y = loc.y
        return this
    }

    override fun setROI() = setROI(screen.bounds)
    override fun setROI(rect: Rectangle) = with(rect) { setROI(x, y, width, height) }
    override fun setROI(region: Region) = with(region) { setROI(x, y, w, h) }
    override fun setROI(X: Int, Y: Int, W: Int, H: Int) {
        x = X
        y = Y
        w = if (W > 1) W else 1
        h = if (H > 1) H else 1
    }

    override fun getCenter() = Location(x + (w / 2), y + (h / 2))
    override fun getTopLeft() = Location(x, y)
    override fun getTopRight() = Location(x + w - 1, y)
    override fun getBottomLeft() = Location(x, y + h - 1)
    override fun getBottomRight() = Location(x + w - 1, y + h - 1)

    override fun getLastMatch() = super.getLastMatch()?.let { SKryptonMatch(it, skryptonScreen()) }
    override fun getLastMatches() =
            super.getLastMatches().asSequence().map { SKryptonMatch(it, skryptonScreen()) }.iterator()

    override fun offset(loc: Location) = SKryptonRegion(super.offset(loc), skryptonScreen())
    override fun grow(l: Int, r: Int, t: Int, b: Int) = SKryptonRegion(super.grow(l, r, t, b), skryptonScreen())
    override fun grow(w: Int, h: Int) = SKryptonRegion(super.grow(w, h), skryptonScreen())
    override fun grow(range: Int) = SKryptonRegion(super.grow(range), skryptonScreen())
    override fun grow() = SKryptonRegion(super.grow(), skryptonScreen())

    override fun union(region: Region) = SKryptonRegion(super.union(region), skryptonScreen())
    override fun intersection(region: Region) = SKryptonRegion(super.intersection(region), skryptonScreen())

    override fun above() = SKryptonRegion(super.above(), skryptonScreen())
    override fun below() = SKryptonRegion(super.below(), skryptonScreen())
    override fun left() = SKryptonRegion(super.left(), skryptonScreen())
    override fun right() = SKryptonRegion(super.right(), skryptonScreen())

    override fun above(height: Int) = SKryptonRegion(super.above(height), skryptonScreen())
    override fun below(height: Int) = SKryptonRegion(super.below(height), skryptonScreen())
    override fun left(width: Int) = SKryptonRegion(super.left(width), skryptonScreen())
    override fun right(width: Int) = SKryptonRegion(super.right(width), skryptonScreen())

    //<editor-fold desc="Search operations">

    override fun <PSI : Any> find(target: PSI) = SKryptonMatch(super.find(target), skryptonScreen())
    override fun <PSI : Any> findAll(target: PSI) =
            super.findAll(target).asSequence().map { SKryptonMatch(it, skryptonScreen()) }.iterator()

    override fun <PSI : Any> wait(target: PSI) = SKryptonMatch(super.wait(target), skryptonScreen())
    override fun <PSI : Any> wait(target: PSI, timeout: Double) = SKryptonMatch(super.wait(target, timeout), skryptonScreen())

    override fun <PSI : Any> exists(target: PSI) = super.exists(target)?.let { SKryptonMatch(it, skryptonScreen()) }
    override fun <PSI : Any> exists(target: PSI, timeout: Double) = super.exists(target, timeout)?.let { SKryptonMatch(it, skryptonScreen()) }

    //</editor-fold>

    //<editor-fold desc="Mouse and Keyboard Actions">
    override fun click(): Int = click(center, 0)

    override fun <PFRML : Any> click(target: PFRML): Int = click(target, 0)
    override fun <PFRML : Any> click(target: PFRML, modifiers: Int): Int = try {
        mouse.click(getLocationFromTarget(target), Mouse.LEFT, modifiers)
        1
    } catch (e: FindFailed) {
        0
    }

    override fun doubleClick(): Int = doubleClick(center, 0)
    override fun <PFRML : Any> doubleClick(target: PFRML): Int = doubleClick(target, 0)
    override fun <PFRML : Any> doubleClick(target: PFRML, modifiers: Int): Int = try {
        mouse.doubleClick(getLocationFromTarget(target), Mouse.LEFT, modifiers)
        1
    } catch (e: FindFailed) {
        0
    }

    override fun rightClick(): Int = rightClick(center, 0)
    override fun <PFRML : Any> rightClick(target: PFRML): Int = rightClick(target, 0)
    override fun <PFRML : Any> rightClick(target: PFRML, modifiers: Int): Int = try {
        mouse.click(getLocationFromTarget(target), Mouse.RIGHT, modifiers)
        1
    } catch (e: FindFailed) {
        0
    }

    override fun hover(): Int = hover(center)
    override fun <PFRML : Any> hover(target: PFRML): Int = try {
        mouse.moveTo(getLocationFromTarget(target))
        1
    } catch (e: FindFailed) {
        0
    }

    override fun <PFRML : Any> dragDrop(target: PFRML): Int = lastMatch?.let { dragDrop(it, target) } ?: 0

    override fun <PFRML : Any> dragDrop(t1: PFRML, t2: PFRML): Int = try {
        mouse.dragDrop(getLocationFromTarget(t1), getLocationFromTarget(t2))
        1
    } catch (e: FindFailed) {
        0
    }

    override fun <PFRML : Any> drag(target: PFRML): Int = try {
        mouse.drag(getLocationFromTarget(target))
        1
    } catch (e: FindFailed) {
        0
    }

    override fun <PFRML : Any> dropAt(target: PFRML): Int = try {
        mouse.dropAt(getLocationFromTarget(target))
        1
    } catch (e: FindFailed) {
        0
    }

    override fun type(text: String): Int = type(text, 0)
    override fun type(text: String, modifiers: String): Int = type(text, SKryptonKeyboard.parseModifiers(modifiers))
    override fun type(text: String, modifiers: Int): Int {
        keyboard.type(null, text, modifiers)
        return 1
    }

    override fun <PFRML : Any> type(target: PFRML, text: String): Int = type(target, text, 0)
    override fun <PFRML : Any> type(target: PFRML, text: String, modifiers: String): Int =
            type(target, text, SKryptonKeyboard.parseModifiers(modifiers))

    override fun <PFRML : Any> type(target: PFRML, text: String, modifiers: Int): Int = try {
        keyboard.type(getLocationFromTarget(target), text, modifiers)
        1
    } catch (e: FindFailed) {
        0
    }


    override fun paste(text: String): Int {
        skryptonScreen().clipboard = text
        keyboard.atomicAction {
            keyDown(Key.getHotkeyModifier())
            keyDown(KeyEvent.VK_V)
            keyUp(KeyEvent.VK_V)
            keyUp(Key.getHotkeyModifier())
        }
        return 0
    }

    override fun <PFRML : Any> paste(target: PFRML, text: String): Int {
        if (text.isEmpty() || click(target) == 1) return 1
        return paste(text)
    }
    //</editor-fold>

    //<editor-fold desc="Low-level Mouse and Keyboard Actions">
    override fun mouseDown(buttons: Int) = mouse.mouseDown(buttons)

    override fun mouseUp() {
        mouse.mouseUp()
    }

    override fun mouseUp(buttons: Int) {
        mouse.mouseUp(buttons)
    }

    override fun mouseMove(): Int = lastMatch?.let { mouseMove(it) } ?: 0
    override fun mouseMove(xoff: Int, yoff: Int): Int = mouseMove(Location(x + xoff, y + yoff))
    override fun <PFRML : Any> mouseMove(target: PFRML): Int = try {
        mouse.moveTo(getLocationFromTarget(target))
        1
    } catch (e: FindFailed) {
        0
    }

    override fun wheel(direction: Int, steps: Int): Int = wheel(skryptonScreen().currentMousePosition(), direction, steps)
    override fun <PFRML : Any> wheel(target: PFRML, direction: Int, steps: Int): Int = wheel(target, direction, steps, Mouse.WHEEL_STEP_DELAY)
    override fun <PFRML : Any> wheel(target: PFRML, direction: Int, steps: Int, stepDelay: Int): Int {
        mouse.spinWheel(getLocationFromTarget(target), direction, steps, stepDelay)
        return 1
    }

    override fun keyDown(keycode: Int) = keyboard.keyDown(keycode)
    override fun keyDown(keys: String) = keyboard.keyDown(keys)

    override fun keyUp() = keyboard.keyUp()
    override fun keyUp(keycode: Int) = keyboard.keyUp(keycode)
    override fun keyUp(keys: String) = keyboard.keyUp(keys)
    //</editor-fold>

    //<editor-fold desc="Highlight Action">
    private val highlighter by lazy { WebViewHighlighter(skryptonScreen().webView, x, y, width, height) }

    override fun highlight() = highlight("")
    override fun highlight(color: String): SKryptonRegion {
        highlighter.color = parseColor(color)
        highlighter.toggle()
        return this
    }

    override fun highlight(secs: Int) = highlight(secs, "")
    override fun highlight(secs: Float) = highlight(secs, "")
    override fun highlight(secs: Int, color: String) = highlight(secs.toFloat(), color)

    override fun highlight(secs: Float, color: String): SKryptonRegion {
        highlighter.color = parseColor(color)
        highlighter.showFor(secs)
        return this
    }

    //</editor-fold>

    override fun <PSIMRL : Any> getLocationFromTarget(target: PSIMRL): Location = when (target) {
        is Pattern, is String, is Image -> find(target).target
        is Match -> target.target
        is SKryptonRegion -> target.center
        is Region -> target.center
        is Location -> target
        else -> throw FindFailed("Not able to get location from $target")
    }.setOtherScreen(screen)

    private fun parseColor(color: String?): Color {
        if (color == null) return Color.RED
        try {
            if (color.startsWith("#")) {
                if (color.length > 7) {
                    if (color.length == 10) {
                        val cR = Integer.decode(color.substring(1, 4))
                        val cG = Integer.decode(color.substring(4, 7))
                        val cB = Integer.decode(color.substring(7, 10))
                        return Color(cR, cG, cB)
                    }
                } else {
                    return Color(Integer.decode(color))
                }
            } else {
                return Class.forName("java.awt.Color").getField(color.replace(" ", "_").toUpperCase()).get(null) as Color
            }
        } catch (e: Exception) {
            return Color.RED
        }
        return Color.RED
    }

    //<editor-fold desc="Kotlin operator overloads">

    override operator fun plus(region: Region) = union(region)
    override operator fun compareTo(region: Region) = (w * h) - region.let { it.w * it.h }

    //</editor-fold>

    //<editor-fold desc="Unsupported"

    //</editor-fold>
}
