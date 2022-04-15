package com.repairzone.newsl.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.repairzone.newsl.data.network.base.BaseAction
import com.repairzone.newsl.data.network.base.BaseEvent
import com.repairzone.newsl.utils.SingleLiveEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

abstract class BaseAndroidViewModel: ViewModel() {
    var progressLiveEvent = SingleLiveEvent<Boolean>()
    var errorEvent = SingleLiveEvent<BaseError>()
    private val actions = Channel<BaseAction>(Channel.UNLIMITED)
    protected val events = Channel<BaseEvent>(Channel.UNLIMITED)

    protected var actionFlow: SharedFlow<BaseAction> =
        actions.consumeAsFlow().shareIn(viewModelScope, SharingStarted.WhileSubscribed())
    val eventFlow get() = events.receiveAsFlow()

    fun dispatch(action: BaseAction){
        actions.trySend(action)
    }

    inline fun <T> launchAsync(
        crossinline execute: suspend () -> Response<T>,
        crossinline onSuccess: (T) -> Unit,
        showProgress: Boolean = true
    ){
        viewModelScope.launch {
            if (showProgress){
                progressLiveEvent.postValue(true)
                try {
                    val result = execute()
                    if (result.isSuccessful){
                        onSuccess(result.body()!!)
                    }else{
                        errorEvent.postValue(BaseError(result.code(), result.message()))
                    }
                }catch (e: Exception){
                    errorEvent.postValue(BaseError(0, e.message, e))
                } finally {
                    progressLiveEvent.postValue(true)
                }
            }
        }
    }

    inline fun <T> launchPagingAsync(
        crossinline execute: suspend () -> Flow<T>,
        crossinline  onSuccess: (Flow<T>) -> Unit
    ){
        viewModelScope.launch {
            try {
                val result = execute()
                onSuccess(result)
            }catch (e: Exception){
                errorEvent.postValue(BaseError(0, e.message, e))
            }
        }
    }

}