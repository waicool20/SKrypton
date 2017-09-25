package com.waicool20.skrypton.jni.objects

import com.waicool20.skrypton.enums.KeyboardModifiers
import com.waicool20.skrypton.enums.MouseButton
import com.waicool20.skrypton.enums.MouseEventSource
import com.waicool20.skrypton.enums.MouseEventType
import com.waicool20.skrypton.jni.CPointer
import java.awt.MouseInfo
import java.awt.Point

class SKryptonMouseEvent private constructor(pointer: Long) : SKryptonEvent() {
    override val handle = CPointer(pointer)

    private companion object {
        private external fun initialize_N(
                type: Int,
                localPosX: Int,
                localPosY: Int,
                windowPosX: Int,
                windowPosY: Int,
                screenPosX: Int,
                screenPosY: Int,
                button: Long,
                buttons: Long,
                modifiers: Long,
                source: Int
        ): Long
    }

    //<editor-fold desc="Constructor">
    constructor(
            type: MouseEventType,
            localPos: Point,
            windowPos: Point = localPos,
            screenPos: Point = MouseInfo.getPointerInfo().location,
            button: MouseButton = MouseButton.NoButton,
            buttons: Set<MouseButton> = setOf(button),
            modifiers: KeyboardModifiers = KeyboardModifiers.NoModifier,
            source: MouseEventSource = MouseEventSource.MouseEventNotSynthesized
    ) : this(initialize_N(
            type.id,
            localPos.x, localPos.y,
            windowPos.x, windowPos.y,
            screenPos.x, screenPos.y,
            button.value,
            if (button == MouseButton.NoButton) {
                MouseButton.NoButton.value
            } else {
                buttons.map { it.value }.reduce { acc, l -> acc or l }
            },
            modifiers.value,
            source.ordinal
    ))
    //</editor-fold>

    val x by lazy { localPos.x }
    val y by lazy { localPos.y }

    val globalX by lazy { globalPos.x }
    val globalY by lazy { globalPos.y }

    val button by lazy { MouseButton.getForValue(getButton_N()) }
    val buttons by lazy {
        val buttons = getButtons_N()
        MouseButton.values().filter { it.value and buttons == it.value }.toSet()
    }
    val globalPos by lazy { getGlobalPos_N() }
    val screenPos by lazy { getScreenPos_N() }
    val localPos by lazy { getLocalPos_N() }
    val source by lazy { MouseEventSource.values()[getSource_N()] }

    private external fun getButton_N(): Long
    private external fun getButtons_N(): Long
    private external fun getGlobalPos_N(): Point
    private external fun getScreenPos_N(): Point
    private external fun getLocalPos_N(): Point
    private external fun getSource_N(): Int
}
