package com.repairzone.newsl.utils.ext

import com.repairzone.newsl.data.network.base.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

inline fun <T,A> performGet(
    crossinline databaseQuery: () -> Flow<T>,
    crossinline networkDataSource: suspend () -> Resource<A>,
    crossinline saveResult: suspend (A?) -> Unit
) = flow {
    emit(Resource.Loading())
    val result = networkDataSource.invoke()
    if (result is Resource.Success){
        saveResult(result.data)
    }
    val flow = try {
        databaseQuery.invoke().map { Resource.Success(it) }
    }catch (t: Throwable){
        databaseQuery.invoke().map { Resource.Error(t, it) }
    }
    emitAll(flow)
}