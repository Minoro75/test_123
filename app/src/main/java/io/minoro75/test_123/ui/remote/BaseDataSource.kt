package io.minoro75.test_123.ui.remote

import io.minoro75.test_123.ui.utils.Resource
import retrofit2.Response
import kotlin.Exception

abstract class BaseDataSource {
protected suspend fun <T> getResult(call: suspend() -> Response<T>):Resource<T>{
    try {
        val response = call()
        if (response.isSuccessful){
            val body = response.body()
            if(body!=null) return Resource.success(body)
        }
        return Resource.error(null, response.message())
    }
    catch (e:Exception){
        return Resource.error(null,e.localizedMessage ?: e.toString())
    }
}
}