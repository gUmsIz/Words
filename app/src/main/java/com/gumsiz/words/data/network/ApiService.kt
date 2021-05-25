package com.gumsiz.words.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

//TODO Base URL for API
private const val BASE_URL = "ENTER API URL"

/**
 * object pointing to the desired URL
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

/**
 * A public interface that exposes the [getWords] method
 */
interface WordApiService {
    /**
     */
    @GET("connect.php")
    suspend fun getWords(): List<WordN>
}

object WordApi {
    val retrofitService: WordApiService by lazy { retrofit.create(WordApiService::class.java) }
}
