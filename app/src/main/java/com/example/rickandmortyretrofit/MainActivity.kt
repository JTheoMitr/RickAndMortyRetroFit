package com.example.rickandmortyretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.rickandmortyretrofit.network.ApiClient
import com.example.rickandmortyretrofit.network.CharacterResponse
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val client = ApiClient.apiService.fetchCharacters("1")

        client.enqueue(object : retrofit2.Callback<CharacterResponse> {

            // on successful response we log results
            override fun onResponse(
                call: Call<CharacterResponse>,
                response: Response<CharacterResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("characters", "" + response.body())
                }
            }
            override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                Log.e("failed", " " + t.message)
            }

        })
    }
}