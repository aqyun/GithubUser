package com.dicoding.githubuser.helper

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.githubuser.database.FavoriteUser

class FavoriteUserDiffCallback : DiffUtil.ItemCallback<FavoriteUser>() {
    override fun areItemsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
        return oldItem == newItem
    }
}
