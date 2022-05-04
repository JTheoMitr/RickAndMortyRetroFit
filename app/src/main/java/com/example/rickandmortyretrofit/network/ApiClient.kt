package com.example.rickandmortyretrofit.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object ApiClient {

    // BASE URL:
    private val BASE_URL = "https://rickandmortyapi.com/api/"

    // Variable for Moshi builder
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    // Create an instance of retrofit by lazy so it can initialize only when needed
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

// API Interface

interface ApiService {
    @GET("character")
    fun fetchCharacters(@Query("page") page:String): Call<CharacterResponse>
}