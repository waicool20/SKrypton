package com.waicool20.skrypton.enums

// Extracted from qnamespace.h
enum class MouseButton(val value: Long) {
    NoButton(0x00000000),
    LeftButton(0x00000001),
    RightButton(0x00000002),
    MidButton(0x00000004),
    MiddleButton(MidButton.value),
    BackButton(0x00000008),
    XButton1(BackButton.value),
    ExtraButton1(XButton1.value),
    ForwardButton(0x00000010),
    XButton2(ForwardButton.value),
    ExtraButton2(ForwardButton.value),
    TaskButton(0x00000020),
    ExtraButton3(TaskButton.value),
    ExtraButton4(0x00000040),
    ExtraButton5(0x00000080),
    ExtraButton6(0x00000100),
    ExtraButton7(0x00000200),
    ExtraButton8(0x00000400),
    ExtraButton9(0x00000800),
    ExtraButton10(0x00001000),
    ExtraButton11(0x00002000),
    ExtraButton12(0x00004000),
    ExtraButton13(0x00008000),
    ExtraButton14(0x00010000),
    ExtraButton15(0x00020000),
    ExtraButton16(0x00040000),
    ExtraButton17(0x00080000),
    ExtraButton18(0x00100000),
    ExtraButton19(0x00200000),
    ExtraButton20(0x00400000),
    ExtraButton21(0x00800000),
    ExtraButton22(0x01000000),
    ExtraButton23(0x02000000),
    ExtraButton24(0x04000000),
    AllButtons(0x07ffffff),
    MaxMouseButton(ExtraButton24.value),
    // 4 high-order bits remain available for future use (0x08000000 through 0x40000000).
    MouseButtonMask(0xffffffff);

    companion object {
        fun getForValue(value: Long) =
                values().find { it.value == value } ?: error("No such MouseButton with value $value")
    }
}
