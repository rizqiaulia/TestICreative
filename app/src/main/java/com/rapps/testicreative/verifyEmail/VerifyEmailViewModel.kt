package com.rapps.testicreative.verifyEmail

import androidx.collection.ArrayMap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.rapps.testicreative.api.ApiInterface
import com.rapps.testicreative.model.StateViewModel
import com.rapps.testicreative.tools.getStringResponse
import com.rapps.testicreative.tools.isJSONValid
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerifyEmailViewModel:ViewModel() {

    var responseVerify = MutableLiveData<StateViewModel>()

    fun verifyEmail(token:String){
        val jsonParams: MutableMap<String, String> =
            ArrayMap()
        jsonParams["token"] = token

        val gson = GsonBuilder().create()
        val jsonString = gson.toJson(jsonParams)
        val body: RequestBody = jsonString
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        ApiInterface.create().verifyEmail(body).enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody?>) {
                val json = getStringResponse(response)
                if (isJSONValid(json)) {
                    var jdata: JSONObject? = null
                    jdata = JSONObject(json)

                    when (response.code()) {
                        200 -> {
                            val message = jdata.get("message").toString()
                            responseVerify.value = StateViewModel.Success(message,200)
                        }
                        else -> {
                            val message = jdata.get("message").toString()
                            responseVerify.value = StateViewModel.Error(message,response.code())
                        }
                    }

                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                responseVerify.value = StateViewModel.Error(t.toString(),999)
            }

        })
    }
}