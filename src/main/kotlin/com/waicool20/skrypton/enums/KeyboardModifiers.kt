package com.waicool20.skrypton.enums

// Extracted from qnamespace.h
enum class KeyboardModifiers(val value: Long) {
    NoModifier(0x00000000),
    ShiftModifier(0x02000000),
    ControlModifier(0x04000000),
    AltModifier(0x08000000),
    MetaModifier(0x10000000),
    KeypadModifier(0x20000000),
    GroupSwitchModifier(0x40000000),
    // Do not extend the mask to include 0x01000000
    KeyboardModifierMask(0xfe000000)
}
