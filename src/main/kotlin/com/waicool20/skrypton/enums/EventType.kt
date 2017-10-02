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

package com.waicool20.skrypton.enums

// Extracted from qcoreevent.h
private enum class EventType(val id: Int) {
    // invalid event
    None(0),
    // timer event
    Timer(1),
    // mouse button pressed
    MouseButtonPress(2),
    // mouse button released
    MouseButtonRelease(3),
    // mouse button double click
    MouseButtonDblClick(4),
    // mouse move
    MouseMove(5),
    // key pressed
    KeyPress(6),
    // key released
    KeyRelease(7),
    // keyboard focus received
    FocusIn(8),
    // keyboard focus lost
    FocusOut(9),
    // keyboard focus is about to be lost
    FocusAboutToChange(23),
    // mouse enters widget
    Enter(10),
    // mouse leaves widget
    Leave(11),
    // paint widget
    Paint(12),
    // move widget
    Move(13),
    // resize widget
    Resize(14),
    // after widget creation
    Create(15),
    // during widget destruction
    Destroy(16),
    // widget is shown
    Show(17),
    // widget is hidden
    Hide(18),
    // request to close widget
    Close(19),
    // request to quit application
    Quit(20),
    // widget has been reparented
    ParentChange(21),
    // sent just before the parent change is done
    ParentAboutToChange(131),
    // object has changed threads
    ThreadChange(22),
    // window was activated
    WindowActivate(24),
    // window was deactivated
    WindowDeactivate(25),
    // widget is shown to parent
    ShowToParent(26),
    // widget is hidden to parent
    HideToParent(27),
    // wheel event
    Wheel(31),
    // window title changed
    WindowTitleChange(33),
    // icon changed
    WindowIconChange(34),
    // application icon changed
    ApplicationWindowIconChange(35),
    // application font changed
    ApplicationFontChange(36),
    // application layout direction changed
    ApplicationLayoutDirectionChange(37),
    // application palette changed
    ApplicationPaletteChange(38),
    // widget palette changed
    PaletteChange(39),
    // internal clipboard event
    Clipboard(40),
    // reserved for speech input
    Speech(42),
    // meta call event
    MetaCall(43),
    // socket activation
    SockAct(50),
    // win event activation
    WinEventAct(132),
    // deferred delete event
    DeferredDelete(52),
    // drag moves into widget
    DragEnter(60),
    // drag moves in widget
    DragMove(61),
    // drag leaves or is cancelled
    DragLeave(62),
    // actual drop
    Drop(63),
    // drag accepted/rejected
    DragResponse(64),
    // new child widget
    ChildAdded(68),
    // polished child widget
    ChildPolished(69),
    // deleted child widget
    ChildRemoved(71),
    // widget's window should be mapped
    ShowWindowRequest(73),
    // widget should be polished
    PolishRequest(74),
    // widget is polished
    Polish(75),
    // widget should be relayouted
    LayoutRequest(76),
    // widget should be repainted
    UpdateRequest(77),
    // request update() later
    UpdateLater(78),
    // ActiveX embedding
    EmbeddingControl(79),
    // ActiveX activation
    ActivateControl(80),
    // ActiveX deactivation
    DeactivateControl(81),
    // context popup menu
    ContextMenu(82),
    // input method
    InputMethod(83),
    // Wacom tablet event
    TabletMove(87),
    // the system locale changed
    LocaleChange(88),
    // the application language changed
    LanguageChange(89),
    // the layout direction changed
    LayoutDirectionChange(90),
    // internal style event
    Style(91),
    // tablet press
    TabletPress(92),
    // tablet release
    TabletRelease(93),
    // CE (Ok) button pressed
    OkRequest(94),
    // CE (?)  button pressed
    HelpRequest(95),
    // proxy icon dragged
    IconDrag(96),
    // font has changed
    FontChange(97),
    // enabled state has changed
    EnabledChange(98),
    // window activation has changed
    ActivationChange(99),
    // style has changed
    StyleChange(100),
    // icon text has changed.  Deprecated.
    IconTextChange(101),
    // modified state has changed
    ModifiedChange(102),
    // mouse tracking state has changed
    MouseTrackingChange(109),
    // window is about to be blocked modally
    WindowBlocked(103),
    // windows modal blocking has ended
    WindowUnblocked(104),
    WindowStateChange(105),
    // readonly state has changed
    ReadOnlyChange(106),
    ToolTip(110),
    WhatsThis(111),
    StatusTip(112),
    ActionChanged(113),
    ActionAdded(114),
    ActionRemoved(115),
    // file open request
    FileOpen(116),
    // shortcut triggered
    Shortcut(117),
    // shortcut override request
    ShortcutOverride(51),
    WhatsThisClicked(118),
    // toolbar visibility toggled
    ToolBarChange(120),
    // deprecated. Use ApplicationStateChange instead.
    ApplicationActivate(121),
    ApplicationActivated(ApplicationActivate.id), // deprecated
    // deprecated. Use ApplicationStateChange instead.
    ApplicationDeactivate(122),
    ApplicationDeactivated(ApplicationDeactivate.id), // deprecated
    // query what's this widget help
    QueryWhatsThis(123),
    EnterWhatsThisMode(124),
    LeaveWhatsThisMode(125),
    // child widget has had its z-order changed
    ZOrderChange(126),
    // mouse cursor enters a hover widget
    HoverEnter(127),
    // mouse cursor leaves a hover widget
    HoverLeave(128),
    // mouse cursor move inside a hover widget
    HoverMove(129),
    // last event id used = 132
    // enter edit mode in keypad navigation
    EnterEditFocus(150),
    // enter edit mode in keypad navigation
    LeaveEditFocus(151),
    AcceptDropsChange(152),
    // Used for Windows Zero timer events
    ZeroTimerEvent(154),
    // GraphicsView
    GraphicsSceneMouseMove(155),
    GraphicsSceneMousePress(156),
    GraphicsSceneMouseRelease(157),
    GraphicsSceneMouseDoubleClick(158),
    GraphicsSceneContextMenu(159),
    GraphicsSceneHoverEnter(160),
    GraphicsSceneHoverMove(161),
    GraphicsSceneHoverLeave(162),
    GraphicsSceneHelp(163),
    GraphicsSceneDragEnter(164),
    GraphicsSceneDragMove(165),
    GraphicsSceneDragLeave(166),
    GraphicsSceneDrop(167),
    GraphicsSceneWheel(168),
    // keyboard layout changed
    KeyboardLayoutChange(169),
    // A dynamic property was changed through setProperty/property
    DynamicPropertyChange(170),
    TabletEnterProximity(171),
    TabletLeaveProximity(172),
    NonClientAreaMouseMove(173),
    NonClientAreaMouseButtonPress(174),
    NonClientAreaMouseButtonRelease(175),
    NonClientAreaMouseButtonDblClick(176),
    // when the Qt::WA_Mac{Normal,Small,Mini}Size changes
    MacSizeChange(177),
    // sent by QWidget::setContentsMargins (internal)
    ContentsRectChange(178),
    // Internal! the window of the GLWidget has changed
    MacGLWindowChange(179),
    FutureCallOut(180),
    GraphicsSceneResize(181),
    GraphicsSceneMove(182),
    CursorChange(183),
    ToolTipChange(184),
    // Internal for QNetworkReply
    NetworkReplyUpdated(185),
    GrabMouse(186),
    UngrabMouse(187),
    GrabKeyboard(188),
    UngrabKeyboard(189),
    // Internal Cocoa, the window has changed, so we must clear
    MacGLClearDrawable(191),
    StateMachineSignal(192),
    StateMachineWrapped(193),
    TouchBegin(194),
    TouchUpdate(195),
    TouchEnd(196),
    // QtGui native gesture
    NativeGesture(197),
    RequestSoftwareInputPanel(199),
    CloseSoftwareInputPanel(200),
    WinIdChange(203),
    Gesture(198),
    GestureOverride(202),
    ScrollPrepare(204),
    Scroll(205),
    Expose(206),
    InputMethodQuery(207),
    // Screen orientation has changed
    OrientationChange(208),
    TouchCancel(209),
    ThemeChange(210),
    // socket closed
    SockClose(211),
    PlatformPanel(212),
    // style animation target should be updated
    StyleAnimationUpdate(213),
    ApplicationStateChange(214),
    // internal for QQuickWidget
    WindowChangeInternal(215),
    ScreenChangeInternal(216),
    // Platform surface created or about to be destroyed
    PlatformSurface(217),
    // QQuickPointerEvent; ### Qt 6: QPointerEvent
    Pointer(218),
    // tablet tracking state has changed
    TabletTrackingChange(219),
    // 512 reserved for Qt Jambi's MetaCall event
    // 513 reserved for Qt Jambi's DeleteOnMainThread event
    // first user event id
    User(1000),
    // last user event id
    MaxUser(65535)
}

enum class MouseEventType(val id: Int) {
    MouseButtonPress(EventType.MouseButtonPress.id),
    MouseButtonRelease(EventType.MouseButtonRelease.id),
    MouseButtonDblClick(EventType.MouseButtonDblClick.id),
    MouseMove(EventType.MouseMove.id);

    companion object {
        fun getForId(id: Int) =
                values().find { it.id == id } ?: error("No such event with id $id")
    }
}

enum class KeyEventType(val id: Int) {
    KeyPress(EventType.KeyPress.id),
    KeyRelease(EventType.KeyRelease.id),
    ShortcutOverride(EventType.ShortcutOverride.id);

    companion object {
        fun getForId(id: Int) =
                values().find { it.id == id } ?: error("No such event with id $id")
    }
}
