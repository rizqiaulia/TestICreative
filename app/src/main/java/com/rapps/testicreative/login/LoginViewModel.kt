package com.rapps.testicreative.login

import androidx.collection.ArrayMap
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.rapps.testicreative.api.ApiInterface
import com.rapps.testicreative.model.LoginResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    fun authenticate(email:String,password:String){

        val jsonParams: MutableMap<String, String> =
            ArrayMap()
        jsonParams["email"] = email
        jsonParams["password"] = password

        val gson = GsonBuilder().create()
        val jsonString = gson.toJson(jsonParams)
        val body: RequestBody = jsonString
            .toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        ApiInterface.create().authenticate(body).enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

            }

        })
    }

}