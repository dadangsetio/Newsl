package com.repairzone.newsl.ui.base

interface OnClickEvent {
    var actionTap: Int
    var onClick: (action: Int) -> Unit
}