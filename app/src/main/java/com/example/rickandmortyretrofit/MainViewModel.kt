package com.example.rickandmortyretrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmortyretrofit.network.ApiClient
import com.example.rickandmortyretrofit.network.Character
import com.example.rickandmortyretrofit.network.CharacterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: Repository = Repository(ApiClient.apiService)) :
    ViewModel() {

    // create a mutable list to alter within this class after making a call
    private var _charactersLiveData = MutableLiveData<ScreenState<List<Character>?>>()

    // create a NON-mutable list for the View to observe that will GET its values from the mutable list - this keeps mutable data sets out of the view directly
    val characterLiveData: LiveData<ScreenState<List<Character>?>>
        get() = _charactersLiveData

    init {
        fetchCharacter()
    }

    private fun fetchCharacter() {

        val client = repository.getCharacters("1")
        _charactersLiveData.postValue(ScreenState.Loading(null))
        client.enqueue(object : Callback<CharacterResponse> {

            override fun onResponse(
                call: Call<CharacterResponse>,
                response: Response<CharacterResponse>
            ) {
                if (response.isSuccessful) {
                    _charactersLiveData.postValue(ScreenState.Success(response.body()?.result))
                } else {
                    _charactersLiveData.postValue(ScreenState.Error(null, response.code().toString()))
                }
            }

            override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                //Log.d("Failure", t.message.toString())
                _charactersLiveData.postValue(ScreenState.Error(null, t.message.toString()))
            }
        })
    }
}