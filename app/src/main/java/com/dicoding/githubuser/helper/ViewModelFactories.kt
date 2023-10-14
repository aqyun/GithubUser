package com.dicoding.githubuser.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubuser.ui.DetailUserViewModel
import com.dicoding.githubuser.ui.FavoriteUserViewModel
import com.dicoding.githubuser.ui.MainViewModel

class ViewModelFactories private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactories? = null
        @JvmStatic
        fun getInstance(application: Application): ViewModelFactories {
            if (INSTANCE == null) {
                synchronized(ViewModelFactories::class.java) {
                    INSTANCE = ViewModelFactories(application)
                }
            }
            return INSTANCE as ViewModelFactories
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailUserViewModel::class.java)) {
            return DetailUserViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(FavoriteUserViewModel::class.java)) {
            return FavoriteUserViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
