package com.repairzone.newsl.data.network.base

import android.util.Log

abstract class BaseException: Throwable{
    constructor(): super()
    constructor(message: String): super(message = message){
        Log.d("Exception" ,message)
    }
    constructor(message: String, cause: Throwable): super(message, cause){
        Log.d("Exception",  message, cause)
    }
    constructor(cause: Throwable): super(cause){
        Log.d("Exception" ,"Something", cause)
    }
}