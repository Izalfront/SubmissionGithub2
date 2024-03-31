package com.example.submissiongithub2.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submissiongithub2.data.database.FavoriteUser
import com.example.submissiongithub2.data.repository.FavRepository

class FavoriteViewModel (application: Application): ViewModel() {
    private val mFavoriteRepository: FavRepository = FavRepository(application)

    fun getAllFavorite(): LiveData<List<FavoriteUser>> = mFavoriteRepository.getAllFavorite()
}