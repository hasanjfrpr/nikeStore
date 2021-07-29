package com.example.digikala.common

import com.example.digikala.R
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.lang.Exception

class NikeExceptionMapper {
    companion object{
        fun map( throwable: Throwable):NikeException {
            if (throwable is HttpException) {
                try {
                    val errorJson=JSONObject(throwable.response()!!.errorBody()!!.string()).getString("message")
                    return NikeException(if (throwable.code()==401) NikeException.Type.AUTH else NikeException.Type.SIMPLE
                        ,serverMessage = errorJson )

                } catch (e: Exception) {

                }
            }
            return NikeException(NikeException.Type.SIMPLE, R.string.unknownError)
        }
    }
}