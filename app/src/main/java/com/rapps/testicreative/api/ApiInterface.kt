package com.rapps.testicreative.api

import com.rapps.testicreative.BuildConfig
import com.rapps.testicreative.model.LoginResponse
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface ApiInterface {

    @POST("/api/register")
    fun register(
        @Body data: RequestBody
    ): Call<ResponseBody>

    @POST("/api/verify-email")
    fun verifyEmail(
        @Body data: RequestBody
    ): Call<ResponseBody>

    @POST("/api/authenticate")
    fun authenticate(
        @Body data: RequestBody
    ): Call<LoginResponse>

    companion object {

        fun create(): ApiInterface {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder()
            httpClient.connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES) // write timeout
                .readTimeout(5, TimeUnit.MINUTES) // read timeout

            httpClient.addInterceptor { chain ->
                val original = chain.request()
                var request: Request? = null
                request = original.newBuilder()
                    .method(original.method, original.body)
                    .build()

                chain.proceed(request)
            }

            val client = httpClient.addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .build()

            return retrofit.create(ApiInterface::class.java)

        }
    }
}