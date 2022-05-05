package com.example.rickandmortyretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.rickandmortyretrofit.network.ApiClient
import com.example.rickandmortyretrofit.network.CharacterResponse
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.characterLiveData.observe(this) { characters ->
            val adapter = MainAdapter(characters)
            val recyclerView = findViewById<RecyclerView>(R.id.charactersRv)

            recyclerView?.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            recyclerView?.adapter = adapter
        }

    }
}