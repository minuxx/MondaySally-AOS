package com.moon.android.mondaysally.data.repository

import com.moon.android.mondaysally.utils.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class BaseNetworkRepository {
    suspend fun <T: Any> apiRequest(call: suspend () -> Response<T>) : T{
        val response = call.invoke()

        if(response.isSuccessful){
            return response.body()!!
        }else{
            val error = response.errorBody()?.string()

            val message = StringBuilder()

            error?.let{
                try{
                    message.append(JSONObject(it).getString("message"))
                }catch (e: JSONException){}
                message.append("\n")
            }
            message.append("Error Code: ${response.code()}")
            throw ApiException(message.toString())
        }
    }

    suspend fun <T: Any> apiRequest2(call: suspend () -> Response<T>, isLoading: Boolean) : T{
        val response = call.invoke()

        if(response.isSuccessful){
            return response.body()!!
        }else{
            val error = response.errorBody()?.string()

            val message = StringBuilder()

            error?.let{
                try{
                    message.append(JSONObject(it).getString("message"))
                }catch (e: JSONException){}
                message.append("\n")
            }
            message.append("Error Code: ${response.code()}")
            throw ApiException(message.toString())
        }
    }
}