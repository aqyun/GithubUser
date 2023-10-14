package com.dicoding.githubuser.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.githubuser.database.FavoriteUser
import com.dicoding.githubuser.repository.FavoriteUserRepository

class FavoriteUserViewModel(application: Application) : AndroidViewModel(application) {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val repository: FavoriteUserRepository = FavoriteUserRepository(application)
    private val allFavoriteUsers: LiveData<List<FavoriteUser>> = repository.getAllFavoriteUsers()

    fun getFavoriteUsers(): LiveData<List<FavoriteUser>> {
        return allFavoriteUsers
    }

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }
}
