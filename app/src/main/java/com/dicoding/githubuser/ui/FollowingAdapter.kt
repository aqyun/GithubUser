package com.dicoding.githubuser.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.databinding.ItemRowFollowBinding
import com.dicoding.githubuser.response.FollowingResponseItem

class FollowingAdapter : ListAdapter<FollowingResponseItem, FollowingAdapter.FollowingViewHolder>(DIFF_CALLBACK) {

    class FollowingViewHolder(private val binding: ItemRowFollowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindFollowing(user: FollowingResponseItem) {
            binding.tvUsername.text = user.login
            Glide.with(binding.root.context)
                .load(user.avatarUrl)
                .into(binding.ivAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val binding = ItemRowFollowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        val user = getItem(position)
        holder.bindFollowing(user)
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FollowingResponseItem>() {
            override fun areItemsTheSame(oldItem: FollowingResponseItem, newItem: FollowingResponseItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FollowingResponseItem, newItem: FollowingResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
