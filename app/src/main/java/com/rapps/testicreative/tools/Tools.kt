package com.rapps.testicreative.tools

import android.util.Log
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

fun isJSONValid(test: String?): Boolean {
    if (test != null) {
        try {
            JSONObject(test)
        } catch (ex: JSONException) {
            Log.e("ValidJSON", ex.toString())
            try {
                JSONArray(test)
            } catch (ex1: JSONException) {
                Log.e("JSONFAIL", ex1.toString())
                return false
            }
        }
        return true
    }
    return false
}

fun getStringResponse(response: Response<ResponseBody?>): String {
    var result = ""
    if (response.body() != null) {
        try {
            result = String(response.body()!!.bytes())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return result
}