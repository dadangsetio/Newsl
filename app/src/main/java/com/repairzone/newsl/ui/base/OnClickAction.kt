package com.repairzone.newsl.ui.base

abstract class OnClickActionModel : OnClickEvent {
    @Transient
    override var onClick: (Int) -> Unit = {}
    override var actionTap: Int
        get() = 1
        set(_) {}
}