package com.dicoding.githubuser.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.database.FavoriteUser
import com.dicoding.githubuser.databinding.ItemRowUserBinding
import com.dicoding.githubuser.helper.FavoriteUserDiffCallback

class FavoriteUserAdapter(private val context: Context) :
    ListAdapter<FavoriteUser, FavoriteUserAdapter.FavoriteUserViewHolder>(FavoriteUserDiffCallback()) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(favoriteUser: FavoriteUser)
    }

    class FavoriteUserViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteUser: FavoriteUser) {
            binding.tvUsername.text = favoriteUser.username
            Glide.with(binding.root.context)
                .load(favoriteUser.avatarUrl)
                .into(binding.ivAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteUserViewHolder, position: Int) {
        val favoriteUser = getItem(position)
        holder.bind(favoriteUser)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(favoriteUser)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailUserActivity::class.java)
            intent.putExtra("username", favoriteUser.username)
            intent.putExtra("avatarUrl", favoriteUser.avatarUrl)
            context.startActivity(intent)
        }
    }

    fun setOnItemClickCallback(callback: OnItemClickCallback) {
        this.onItemClickCallback = callback
    }
}
