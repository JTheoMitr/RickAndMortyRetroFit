package com.example.rickandmortyretrofit

import com.example.rickandmortyretrofit.network.ApiService

class Repository(private val apiService: ApiService) {

    suspend fun getCharacters(page:String) = apiService.fetchCharacters(page)
}