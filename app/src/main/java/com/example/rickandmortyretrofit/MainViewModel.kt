package com.example.rickandmortyretrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyretrofit.network.ApiClient
import com.example.rickandmortyretrofit.network.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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

        _charactersLiveData.postValue(ScreenState.Loading(null))

        viewModelScope.launch(Dispatchers.IO) {
            Log.d("MainViewModel", Thread.currentThread().name)
            try {
                val client = repository.getCharacters("1")
                _charactersLiveData.postValue(ScreenState.Success(client.result))
            } catch (e: Exception) {
                _charactersLiveData.postValue(ScreenState.Error(null, e.message.toString()))
            }
        }
    }

}