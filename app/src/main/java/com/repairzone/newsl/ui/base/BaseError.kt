package com.repairzone.newsl.ui.base

data class BaseError(
    val code: Int,
    val message: String?,
    val throwable: Throwable? = null
)