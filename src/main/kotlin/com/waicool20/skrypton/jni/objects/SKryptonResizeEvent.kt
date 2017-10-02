package com.waicool20.skrypton.jni.objects

import com.waicool20.skrypton.jni.CPointer
import java.awt.Dimension

class SKryptonResizeEvent private constructor(pointer: Long) : SKryptonEvent() {
    override val handle = CPointer(pointer)

    val newSize = getNewSize_N()
    val oldSize = getOldSize_N()

    private external fun getNewSize_N(): Dimension
    private external fun getOldSize_N(): Dimension
}
