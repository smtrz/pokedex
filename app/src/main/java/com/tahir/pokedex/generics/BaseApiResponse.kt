package com.tahir.pokedex.generics

import retrofit2.Response

/**
 * abstract class with method for calling the API and return the response wrapped in sealed class of
 * ResponseResult
 */
abstract class BaseApiResponse {

    suspend fun <T : Any> safeApiCall(apiCall: suspend () -> Response<T>): ResponseResult<T> {
        try {

            val response = apiCall()

            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return ResponseResult.Success(body)
                }
            }

            return ResponseResult.Error("${response.code()} ${response.message()}")
        } catch (e: Exception) {

            return ResponseResult.Error(e.message ?: e.toString())
        }
    }


}