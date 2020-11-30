package com.rapps.testicreative.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.rapps.testicreative.api.ApiInterface
import com.rapps.testicreative.model.Register
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

class RegisterViewModel : ViewModel() {

    var responseRegister = MutableLiveData<StateViewModel>()

    fun registerUser(register: Register) {

        val gson = GsonBuilder().create()
        val jsonString = gson.toJson(register)
        val body: RequestBody = jsonString
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        responseRegister.value = StateViewModel.Loading
            ApiInterface.create().register(body).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody?>) {
                val json = getStringResponse(response)
                if (isJSONValid(json)) {
                    var jdata: JSONObject? = null
                    jdata = JSONObject(json)

                    when (response.code()) {
                        200 -> {
                            val message = jdata.get("message").toString()
                            responseRegister.value = StateViewModel.Success(message,200)
                        }
                        else -> {
                            val message = jdata.get("title").toString()
                            responseRegister.value = StateViewModel.Error(message,response.code())
                        }
                    }

                }else{
                    responseRegister.value = StateViewModel.Error("Something error",response.code())
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                responseRegister.value = StateViewModel.Error(t.localizedMessage,999)
            }

        })
    }

}

