package com.dicoding.githubuser.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.database.FavoriteUser
import com.dicoding.githubuser.repository.FavoriteUserRepository

class DetailUserViewModel(application: Application) : ViewModel() {
    private val favoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun insert(favoriteUser: FavoriteUser) {
        favoriteUserRepository.insert(favoriteUser)
    }

    fun delete(favoriteUser: FavoriteUser) {
        favoriteUserRepository.delete(favoriteUser)
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> {
        return favoriteUserRepository.getFavoriteUserByUsername(username)
    }
}
